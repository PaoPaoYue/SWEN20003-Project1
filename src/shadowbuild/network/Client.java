package shadowbuild.network;

import com.vecsight.dragonite.sdk.config.DragoniteSocketParameters;
import com.vecsight.dragonite.sdk.exception.ConnectionNotAliveException;
import com.vecsight.dragonite.sdk.exception.IncorrectSizeException;
import com.vecsight.dragonite.sdk.exception.SenderClosedException;
import com.vecsight.dragonite.sdk.socket.DragoniteClientSocket;
import com.vecsight.dragonite.sdk.socket.DragoniteSocket;
import com.vecsight.dragonite.utils.type.UnitConverter;
import shadowbuild.control.ClientController;
import shadowbuild.helper.Logger;
import shadowbuild.network.message.ConnectMessage;
import shadowbuild.network.message.Message;
import shadowbuild.network.message.MessageParser;
import shadowbuild.network.message.MessageType;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client {

    private static final int Mbps = 1;

    private Lock lock = new ReentrantLock();

    private InetSocketAddress bindAddress;
    private DragoniteClientSocket clientSocket;
    private boolean doReceive;
    private Thread receiveThread;

    private ClientController clientNetworkController;

    public Client(String hostname, int port) {
        bindAddress = new InetSocketAddress(hostname, port);
        clientNetworkController = ClientController.getInstance();
        try {
            DragoniteSocketParameters parameters = new DragoniteSocketParameters();
            parameters.setReceiveTimeoutSec(1);
            clientSocket = new DragoniteClientSocket(bindAddress, UnitConverter.mbpsToSpeed(Mbps), parameters);
            try {
                send(new ConnectMessage(clientNetworkController.getMainPlayer().getPlayerName()), null);
                byte[] data = clientSocket.read();
                if (MessageParser.toMessageType(data).equals(MessageType.CONNECT_RESPONSE)) {
                    clientNetworkController.onReceiveConnectResponse(MessageParser.toMessage(data));
                }
                else {
                    Logger.error("Client connect failed...unknown connect response message");
                    close();
                    clientNetworkController.onConnectionFailed();
                }
            } catch (InterruptedException | ConnectionNotAliveException e) {
                Logger.warn("Client connect failed");
                close();
                clientNetworkController.onConnectionFailed();
            }
            startReceive(clientSocket);
        } catch (SocketException e) {
            Logger.error("Client initialize failed!!!");
        }
    }

    private void startReceive(DragoniteSocket socket) {
        doReceive = true;
        receiveThread = new Thread(() -> {
            while (doReceive) {
                if(socket.isAlive()){
                    try {
                        byte[] data = socket.read();
                        switch (MessageParser.toMessageType(data)) {
                            case CONNECT_RESPONSE:
                                clientNetworkController.onReceiveConnectResponse(MessageParser.toMessage(data));
                            case ADD_PLAYER:
                                clientNetworkController.onReceiveAddPlayer(MessageParser.toMessage(data));
                                break;
                            case DELETE_PLAYER:
                                clientNetworkController.onReceiveDeletePlayer(MessageParser.toMessage(data));
                                break;
                            case TEXT:
                                clientNetworkController.onReceiveText(MessageParser.toMessage(data));
                                break;
                            case START:
                                clientNetworkController.onReceiveStart(MessageParser.toMessage(data));
                                break;
                            case PUBLISH_PLAYERS:
                                clientNetworkController.onReceivePublishPlayer(MessageParser.toMessage(data));
                                break;
                            case PUBLISH_GAME:
                                clientNetworkController.onReceivePublishGame(MessageParser.toMessage(data));
                                break;
                            case END:
                                clientNetworkController.onReceiveEnd(MessageParser.toMessage(data));
                                break;
                            default:
                                Logger.error("Unknown message type received");
                        }
                    } catch (InterruptedException e) {
                        Logger.error("Unable to receive Dragonite connection messages");
                        close();
                        break;
                    } catch (ConnectionNotAliveException e) {
                        Logger.error("Dragonite connection is not alive");
                        close();
                        break;
                    }
                }
                else {
                    Logger.warn("Dragonite connection closed by accident");
                    break;
                }
            }
        });
        receiveThread.start();
    }

    public <T extends Message> void send(T message, ClientFailureCallback callback) {
        if(clientSocket.isAlive()) {
            try {
                clientSocket.send(MessageParser.toBytes(message));
            } catch (InterruptedException | IncorrectSizeException | IOException e) {
                Logger.error("Sending message... fatal exception happened");
                close();
                if(callback != null)callback.run();
            } catch (SenderClosedException e) {
                Logger.error("Sending message... Socket receiver lost connection");
                close();
                if(callback != null)callback.run();
            }
        } else {
            Logger.warn("Sending message... Dragonite connection closed by accident");
        }


    }

    public void close() {
        lock.lock();
        if(!doReceive) return;
        doReceive = false;
        if (receiveThread != null) {
            receiveThread.interrupt();
        }
        try {
            clientSocket.closeGracefully();
        } catch (InterruptedException | IOException | SenderClosedException e) {}

        clientNetworkController.onConnectionLost();
        lock.unlock();
    }


}
