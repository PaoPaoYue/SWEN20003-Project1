package shadowbuild.control;

import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import shadowbuild.helper.Logger;
import shadowbuild.main.App;
import shadowbuild.network.Server;
import shadowbuild.network.message.*;
import shadowbuild.player.Player;

import java.util.*;

public class ServerController {

    private static final int PUBLISH_INTERVAL = 100;

    Player mainPlayer;
    List<Player> otherPlayers;
    InputController mainInput;
    Map<Player, NetworkInputController> othersInputs;

    private Server server;
    private boolean onConnect;
    private boolean onGame;
    private Thread publishPlayersThread;
    private Thread publishGameThread;

    /** Singleton pattern */
    private static ServerController instance;


    public static ServerController getInstance(){
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }

    private ServerController() {
        otherPlayers = new ArrayList<>();
        othersInputs = new HashMap<>();

        publishPlayersThread = new Thread(()->{
            while (onConnect) {
                try {
                    Thread.sleep(PUBLISH_INTERVAL);
                } catch (InterruptedException e) {}
                int[] ids = new int[otherPlayers.size()+1];
                int[] delays = new int[otherPlayers.size()+1];
                int i = 0;
                for (Player player: getAllPlayers()) {
                    int delay = (int) server.getDelay(player.getId());
                    player.setDelay(delay);
                    ids[i] = player.getId();
                    delays[i] = delay;
                    i++;
                }
                PublishPlayersMessage message = new PublishPlayersMessage(ids,delays);
                server.publish(new PublishPlayersMessage(ids,delays),null);
            }
        });
        publishGameThread = new Thread(()->{
            while (onGame) {
                try {
                    Thread.sleep(PUBLISH_INTERVAL);
                } catch (InterruptedException e) {}
                for (Player otherPlayer: getOtherPlayers()) {
                    int[] ids = new int[otherPlayers.size()];
                    List<List<Input>> inputs = new ArrayList<>();

                    int i = 0;
                    ids[i] = 0;
                    inputs.add(mainInput.getInputQueue());
                    for (Player player: getOtherPlayers()) {
                        if (otherPlayer.equals(player)) continue;
                        ids[i] = player.getId();
                        inputs.add(othersInputs.get(player).getInputQueue());
                        i++;
                    }
                    server.send(otherPlayer.getId(), new PublishGameMessage(ids, inputs),null);
                }
            }
        });
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
        return new ArrayList<>(otherPlayers);
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

    public void startServer() {
        server = new Server();
        onConnect = true;
        publishPlayersThread.start();
    }

    public void closeServer() {
        onConnect = false;
        onGame = false;
        if (!publishPlayersThread.isAlive()) {
            publishPlayersThread.interrupt();
        }
        if (!publishGameThread.isAlive()) {
            publishPlayersThread.interrupt();
        }
        server.close();
    }

    public void startGame() {
        onConnect = false;
        publishPlayersThread.interrupt();
        server.publish(new StartMessage(), null);
        onGame = true;
        publishGameThread.start();
        GameController.setServer();
        App.game.enterState(1, new FadeOutTransition(), new FadeInTransition());
    }

    public void onReceiveConnect(ConnectMessage message, int id) {
        Player newPlayer = new Player(message.getUsername());
        newPlayer.setId(id);

        AddPlayerMessage newPlayerMessage = new AddPlayerMessage(new int[]{newPlayer.getId()}, new String[]{newPlayer.getPlayerName()});
        int[] ids = new int[otherPlayers.size()+1];
        String[] names = new String[otherPlayers.size()+1];
        int i = 0;
        for(Player player: getAllPlayers()) {
            if (player.getId() != 0)
                server.send(player.getId(), newPlayerMessage,null);
            ids[i] = player.getId();
            names[i] = player.getPlayerName();
            i++;

        }
        server.send(id, new AddPlayerMessage(ids, names), null);

        addPlayer(newPlayer);
    }

    public void onConnectionLost(int id) {
        for (Player player: otherPlayers) {
            if (player.getId() == id)
                Logger.info("Player [{0}] left the game", player.getPlayerName());
        }
        deletePlayer(id);
    }

    public void onReceiveText(TextMessage message) {
        server.publish(message, null);
    }

    public void onReceivePostGame(PostGameMessage message) {
        for (Map.Entry<Player, NetworkInputController> entry: othersInputs.entrySet()) {
            if(entry.getKey().getId() == message.getId())
                entry.getValue().addInputQueue(message.getInputList());
        }
    }

}
