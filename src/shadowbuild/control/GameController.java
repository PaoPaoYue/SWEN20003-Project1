package shadowbuild.control;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import shadowbuild.camera.Camera;
import shadowbuild.gui.GUI;
import shadowbuild.player.Player;
import shadowbuild.terrain.Terrain;

/**
 * This class should be used to contain all the different objects in your game control, and schedule their interactions.
 */
public class GameController {

    private Terrain mainTerrain;
    private Camera mainCamera;
    private Player mainPlayer;
    private GUI gameUI;
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
        mainTerrain = new Terrain();
        mainCamera = new Camera();
        mainPlayer = new Player("mainPlayer");
        gameUI = new GUI();
        spritesController = SpritesController.getInstance();
    }

    public static Terrain getMainTerrain() {
        return getInstance().mainTerrain;
    }

    public static Camera getMainCamera() {
        return getInstance().mainCamera;
    }

    public static Player getMainPlayer() {
        return getInstance().mainPlayer;
    }

    public void init(){
        spritesController.init();
        mainCamera.init();
        mainPlayer.getInfo().changeMetalAmount(1000);
    }

    public void update(Input input, int delta){
        spritesController.update(input, delta);
        mainCamera.update(input, delta);
    }

    public void render(Graphics g){
        mainTerrain.render(mainCamera);
        spritesController.render(mainCamera);
        gameUI.render(g);
    }
}
