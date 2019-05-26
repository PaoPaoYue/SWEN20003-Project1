package shadowbuild.control;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import shadowbuild.camera.Camera;
import shadowbuild.gui.GUI;
import shadowbuild.gui.GameUI;
import shadowbuild.gui.UIstate;
import shadowbuild.helper.Logger;
import shadowbuild.player.Player;
import shadowbuild.terrain.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class should be used to contain all the different objects in your game control, and schedule their interactions.
 */
public class GameController {

    private static boolean isServer;

    private Terrain mainTerrain;
    private Camera mainCamera;
    private GameUI gameUI;
    private Player mainPlayer;
    private List<Player> otherPlayers;
    private InputController mainInput;
    private Map<Player, NetworkInputController> othersInputs;
    private SpritesController spritesController;

    /** Singleton pattern */
    private static GameController instance;

    public static GameController getInstance(){
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    private GameController() {
    }

    public static void setClient() {
        isServer = false;

        ClientController clientController = ClientController.getInstance();
        getInstance().mainPlayer = clientController.mainPlayer;
        getInstance().otherPlayers = clientController.otherPlayers;
        getInstance().mainInput = clientController.mainInput;
        getInstance().othersInputs =  clientController.othersInputs;
        getInstance().gameUI = new GameUI(UIstate.CLIENT_GAME);
        getInstance().spritesController = SpritesController.getInstance();
        getInstance().mainTerrain = new Terrain();
        getInstance().mainCamera = new Camera();

        getInstance().spritesController.init();
        getInstance().mainCamera.init();
    }

    public static void setServer() {
        isServer = true;

        ServerController serverController = ServerController.getInstance();
        getInstance().mainPlayer = serverController.mainPlayer;
        getInstance().otherPlayers = serverController.otherPlayers;
        getInstance().mainInput = serverController.mainInput;
        getInstance().othersInputs =  serverController.othersInputs;
        getInstance().gameUI = new GameUI(UIstate.SERVER_GAME);
        getInstance().spritesController = SpritesController.getInstance();
        getInstance().mainTerrain = new Terrain();
        getInstance().mainCamera = new Camera();

        getInstance().spritesController.init();
        getInstance().mainCamera.init();
    }

    public static boolean isServer() {
        return isServer;
    }


    public static Player getMainPlayer() {
        return getInstance().mainPlayer;
    }

    public static List<Player> getOtherPlayers() {
        return getInstance().otherPlayers;
    }

    public static List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        players.add(getMainPlayer());
        players.addAll(getOtherPlayers());
        return players;
    }

    public static Terrain getMainTerrain() {
        return getInstance().mainTerrain;
    }

    public static Camera getMainCamera() {
        return getInstance().mainCamera;
    }

    public static GameUI getGameUI() {return getInstance().gameUI;}

    public void init(){

    }

    public void update(Input input, int delta){
        mainInput.update(input,delta);
        for (NetworkInputController otherInput: othersInputs.values()){
            otherInput.update(delta);
        }
        mainCamera.update(input, delta);
        spritesController.update(mainInput, othersInputs, delta);
    }

    public void render(Graphics g){
        mainTerrain.render(mainCamera);
        spritesController.render(mainCamera, g);
        gameUI.render(g);
    }
}
