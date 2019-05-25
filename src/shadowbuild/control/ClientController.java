package shadowbuild.control;

import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import shadowbuild.main.App;
import shadowbuild.network.Client;
import shadowbuild.network.message.*;
import shadowbuild.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientController {

    public enum ConnectState {
        NO_CONNECTION, CONNECT_FAILED, CONNECTING, CONNECT_INTERRUPTED
    }
    Player mainPlayer;
    List<Player> otherPlayers;
    InputController mainInput;
    Map<Player, NetworkInputController> othersInputs;

    private ConnectState connectState;
    private Client client;

    /** Singleton pattern */
    private static ClientController instance;

    public static ClientController getInstance(){
        if (instance == null) {
            instance = new ClientController();
        }
        return instance;
    }

    private ClientController() {
        connectState = ConnectState.NO_CONNECTION;
        otherPlayers = new ArrayList<>();
        othersInputs = new HashMap<>();
    }

    public ConnectState getConnectState() {
        return connectState;
    }

    public Player getMainPlayer() {
        return mainPlayer;
    }

    public void setMainPlayer(Player mainPlayer) {
        this.mainPlayer = mainPlayer;
        mainPlayer.getGameInfo().changeMetalAmount(1000);
        this.mainInput = new InputController();
    }

    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        players.add(mainPlayer);
        players.addAll(otherPlayers);
        return players;
    }

    public List<Player> getOtherPlayers() {
        return otherPlayers;
    }

    public void addPlayer(Player player) {
        otherPlayers.add(player);
        player.getGameInfo().changeMetalAmount(1000);
        othersInputs.put(player, new NetworkInputController());
    }

    public void deletePlayer(int id) {
        for (int i = 0; i < otherPlayers.size(); i++) {
            if (otherPlayers.get(i).getId() == id){
                otherPlayers.remove(i);
                break;
            }
        }
    }

    public void startClient(String hostname, int port) {
        client = new Client(hostname, port);
    }

    public void CloseClient() {
        connectState = ConnectState.NO_CONNECTION;
        client.close();
    }

    public void onConnectionFailed() {
        connectState = ConnectState.CONNECT_FAILED;
    }

    public void onConnectionLost() {
        otherPlayers.clear();
        connectState = ConnectState.NO_CONNECTION;
    }

    public void onConnectionInterrupted() {
        connectState = ConnectState.CONNECT_INTERRUPTED;
    }

    public void onReceiveConnectResponse(ConnectResponseMessage message) {
        connectState = ConnectState.CONNECTING;
        mainPlayer.setId(message.getId());
    }

    public void onReceiveAddPlayer(AddPlayerMessage message) {
        for (Player player: message.otherPlayers()) {
            addPlayer(player);
        }
    }

    public void onReceiveDeletePlayer(DeletePlayerMessage message) {
        deletePlayer(message.getId());
    }

    public void onReceiveText(TextMessage message) {

    }

    public void onReceiveStart(StartMessage message) {
        GameController.setClient();
        App.game.enterState(1, new FadeOutTransition(), new FadeInTransition());
    }

    public void onReceivePublishPlayer(PublishPlayersMessage message) {
        for (Player player: getAllPlayers()) {
            player.setDelay(message.playerDelay(player.getId()));
        }
    }

    public void onReceivePublishGame(PublishGameMessage message) {
        for(Player player: otherPlayers) {
            othersInputs.get(player).addInputQueue(message.playerInputList(player.getId()));
        }
    }

    public void onReceiveEnd(EndMessage message) {

    }
}
