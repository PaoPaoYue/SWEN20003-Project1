package shadowbuild.network;


import com.vecsight.dragonite.sdk.config.DragoniteSocketParameters;
import com.vecsight.dragonite.sdk.exception.ConnectionNotAliveException;
import com.vecsight.dragonite.sdk.exception.IncorrectSizeException;
import com.vecsight.dragonite.sdk.exception.SenderClosedException;
import com.vecsight.dragonite.sdk.socket.DragoniteServer;
import com.vecsight.dragonite.sdk.socket.DragoniteSocket;
import com.vecsight.dragonite.utils.type.UnitConverter;
import shadowbuild.control.ServerController;
import shadowbuild.helper.Logger;
import shadowbuild.network.message.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {

    private static final int SEVER_PORT = 9000;
    private static final int WEB_PANEL_PORT = 9009;
    private static final int Mbps = 1;

    private static final int MAX_PLAYERS = 4;

    private Lock lock = new ReentrantLock();

    private InetSocketAddress bindAddress;
    private DragoniteServer server;
    private boolean doAccept;
    private boolean doReceive;
    private Map<Integer, DragoniteSocket> socketMap;
    private Thread acceptThread;
    private Map<Integer,Thread> receiveThreadMap;

    private ServerController serverNetworkController;
    private int curId = 1;


    public Server() {
        this.bindAddress = new InetSocketAddress(SEVER_PORT);
        socketMap = new HashMap<>();
        receiveThreadMap = new HashMap<>();
        serverNetworkController = ServerController.getInstance();
        try {
            DragoniteSocketParameters parameters = new DragoniteSocketParameters();
            parameters.setReceiveTimeoutSec(1);
            parameters.setEnableWebPanel(true);
            parameters.setWebPanelBindAddress(new InetSocketAddress(WEB_PANEL_PORT));
            server = new DragoniteServer(bindAddress.getAddress(), bindAddress.getPort(), UnitConverter.mbpsToSpeed(Mbps), parameters);
            startAccept();
        } catch (SocketException e) {
            Logger.error("Server initialize failed!!!");
        }
    }

    private void startAccept() {
        doAccept = true;
        acceptThread = new Thread(() -> {
            try {
                DragoniteSocket socket;
                while (doAccept && (socket = server.accept()) != null && curId < MAX_PLAYERS) {
                    byte[] data = socket.read();
                    if (MessageParser.toMessageType(data).equals(MessageType.CONNECT)) {
                        socket.send(MessageParser.toBytes(new ConnectResponseMessage(curId)));
                        socketMap.put(curId, socket);
                        ConnectMessage message = MessageParser.toMessage(data);
                        serverNetworkController.onReceiveConnect(message, curId);
                        startReceive(curId);
                        Logger.info("New player [{0}] from {1}", message.getUsername(), socket.getRemoteSocketAddress().toString());
                        curId++;
                    }
                }
            } catch (final InterruptedException e) {
                Logger.error("Unable to accept new Dragonite connections");
            } catch (ConnectionNotAliveException | IncorrectSizeException | SenderClosedException | IOException e) {
                Logger.error("Unable to initiate new Dragonite connections");
            }
        });
        acceptThread.start();
    }

    private void startReceive(int id) {
        DragoniteSocket socket = socketMap.get(id);
        doReceive = true;
        Thread receiveThread = new Thread(() -> {
            while (doReceive) {
                if(socket.isAlive()){
                    try {
                        byte[] data = socket.read();
                        switch (MessageParser.toMessageType(data)) {
                            case TEXT:
                                serverNetworkController.onReceiveText(MessageParser.toMessage(data));
                                break;
                            case POST_GAME:
                                serverNetworkController.onReceivePostGame(MessageParser.toMessage(data));
                                break;
                            default:
                                Logger.warn("Unknown message type received");
                        }
                    } catch (InterruptedException e) {
                        Logger.error("Unable to receive Dragonite connection messages");
                        closeConnect(id);
                        break;
                    } catch (ConnectionNotAliveException e) {
                        Logger.error("Dragonite connection is not alive");
                        closeConnect(id);
                        break;
                    }
                }
                else {
                    Logger.error("Dragonite connection closed by accident");
                    break;
                }
            }
        });
        receiveThreadMap.put(curId,receiveThread);
        receiveThread.start();

    }

    private void closeConnect(int id) {
        lock.lock();
        if (socketMap.get(id) == null || receiveThreadMap.get(id) == null)
            return;
        try {
            socketMap.get(id).closeGracefully();
        } catch (InterruptedException | IOException | SenderClosedException e1) { }
        receiveThreadMap.get(id).interrupt();
        receiveThreadMap.remove(id);
        serverNetworkController.onConnectionLost(id);
        publish(new DeletePlayerMessage(id), this::closeConnect);
        lock.unlock();
    }

    public <T extends Message> void send(int id, T message, ServerFailureCallback callback) {
        DragoniteSocket socket = socketMap.get(id);
        if(socket.isAlive()) {
            try {
                socket.send(MessageParser.toBytes(message));
            } catch (InterruptedException | IncorrectSizeException | IOException e) {
                Logger.error("Sending message... fatal exception happened");
                closeConnect(id);
                if(callback != null)callback.run(id);
            } catch (SenderClosedException e) {
                Logger.error("Sending message... Socket receiver lost connection");
                closeConnect(id);
                if (callback != null) callback.run(id);
            }
        } else {
            Logger.warn("Sending message... Dragonite connection closed by accident");
        }
    }

    public <T extends Message> void publish(T message, ServerFailureCallback callback) {
        for (Map.Entry<Integer, DragoniteSocket> entry: socketMap.entrySet()) {
            if(entry.getValue().isAlive())
                send(entry.getKey(), message, callback);
        }
    }

    public long getDelay(int id) {
        if(!socketMap.containsKey(id)) return 0;
        else return socketMap.get(id).getStatistics().getEstimatedRTT();
    }

    public void close() {
        doAccept = false;
        doReceive = false;
        acceptThread.interrupt();
        for (Thread thread: receiveThreadMap.values()) {
            thread.interrupt();
        }
        receiveThreadMap.clear();
        socketMap.clear();
        server.destroy();
    }


}
