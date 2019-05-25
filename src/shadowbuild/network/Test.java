package shadowbuild.network;

import com.alibaba.fastjson.JSON;
import com.vecsight.dragonite.sdk.exception.IncorrectSizeException;
import com.vecsight.dragonite.sdk.exception.SenderClosedException;
import com.vecsight.dragonite.sdk.socket.DragoniteSocket;
import shadowbuild.control.Input;
import shadowbuild.network.message.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;

public class Test {

    private static final long INIT_SEND_SPEED = 102400;
    private static final int SEVER_PORT = 9000;
    private static final int WEB_PANEL_PORT = 9009;

    private static InetSocketAddress bindAddress;

    public Test() {}

    public static void startPublish(DragoniteSocket socket) {
        new Thread(() -> {
            while (socket.isAlive()) {
                try {
                    socket.send("dongs das".getBytes());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IncorrectSizeException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SenderClosedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
    }

    public static void main(String args[]) {

//        PostGameMessage message = new PostGameMessage(0, Input.NO_INPUT);
//        String json = JSON.toJSONString(message);
//        System.out.println(json.length());
//        System.out.println(json);
//        byte[] bytes = MessageParser.toBytes(message);
//        System.out.println(bytes.length);
//        System.out.println(Arrays.toString(bytes));
//        PostGameMessage receivedMessage = MessageParser.toMessage(bytes);
//        System.out.println(receivedMessage.getInputList().isKeyPressed(Input.KEY_1));
//        System.out.println(receivedMessage.getInputList().isKeyPressed(Input.KEY_2));
//        System.out.println(receivedMessage.getInputList().isKeyPressed(Input.KEY_3));

//        AddPlayerMessage message = new AddPlayerMessage(new int[]{1},new String[]{"233333"});
//        for (Player player: message.getPlayers()) {
//            Logger.log(player.getPlayerName());
//        }
//        byte[] bytes = JSON.toJSONBytes(message);
//        AddPlayerMessage receivedMessage = JSON.parseObject(bytes, AddPlayerMessage.class);
//        String json = JSON.toJSONString(message);
//        System.out.println(json);
//        AddPlayerMessage receivedMessage = JSON.parseObject(json, AddPlayerMessage.class);


//        byte[] bytes = MessageParser.toBytes(message);
//        AddPlayerMessage receiveMessage = MessageParser.toMessage(bytes);
//        for (Player player: receiveMessage.getPlayers()) {
//            Logger.log(player.getPlayerName());
//        }

//        TextMessage message = new TextMessage(0,"233333");
//        byte[] bytes = MessageParser.toBytes(message);
//        TextMessage receiveMessage = MessageParser.toMessage(bytes);
//        Logger.log(message.getText());

//        bindAddress = new InetSocketAddress(SEVER_PORT);
//        try {
//            DragoniteSocketParameters parameters = new DragoniteSocketParameters();
//            parameters.setEnableWebPanel(true);
//            parameters.setWebPanelBindAddress(new InetSocketAddress(WEB_PANEL_PORT));
//            DragoniteServer server = new DragoniteServer(bindAddress.getAddress(), bindAddress.getPort(), INIT_SEND_SPEED, parameters);
//
//
//            new Thread(() -> {
//                DragoniteSocket socket;
//                try {
//                    while ((socket = server.accept()) != null) {
//                        Logger.info("New client from {0}", socket.getRemoteSocketAddress().toString());
//                        ConnectMessage message1 = MessageParser.toMessage(socket.read()) ;
//                        Logger.log(message1.getUsername());
//                        try {
//                            socket.send("Connect Success".getBytes());
//                            startPublish(socket);
//                        } catch (IncorrectSizeException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (SenderClosedException e) {
//                            e.printStackTrace();
//                        }
////                        try {
////                            Logger.info("client said: " + new String(socket.read()));
////                        } catch (ConnectionNotAliveException e) {
////                            e.printStackTrace();
////                        }
//                    }
//                    Logger.info("No more client");
//                } catch (final InterruptedException e) {
//                    Logger.error("Unable to accept Dragonite connections");
//                } catch (ConnectionNotAliveException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            DragoniteClientSocket clientSocket = new DragoniteClientSocket(new InetSocketAddress("192.168.137.1",9000),102400, new DragoniteSocketParameters());
//            clientSocket.send(MessageParser.toBytes(new ConnectMessage("233333")));
//            new Thread(() -> {
//                long curTick = new Date().getTime();
//                while(clientSocket.isAlive()) {
//                    try {
//                        String receive = new String(clientSocket.read());
//                        if (receive.equals("Connect Success")) {
//                            Logger.log("connect success!!!");
//                        } else {
//                            Logger.log("server publish: {0}", receive);
//                        }
//                        Logger.log(new Date().getTime() - curTick);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ConnectionNotAliveException e) {
//                        e.printStackTrace();
//                    }
//                }
//                Logger.log("connect time out!!!");
//                try {
//                    clientSocket.closeGracefully();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (SenderClosedException e) {
//                    e.printStackTrace();
//                }
//            }).start();
////            try {
////                clientSocket.send("23333330".getBytes());
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            } catch (IncorrectSizeException e) {
////                e.printStackTrace();
////            } catch (IOException e) {
////                e.printStackTrace();
////            } catch (SenderClosedException e) {
////                e.printStackTrace();
////            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (IncorrectSizeException e) {
//            e.printStackTrace();
//        } catch (SenderClosedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
