package shadowbuild.control;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import shadowbuild.camera.Camera;
import shadowbuild.terrain.Terrain;

/**
 * This class should be used to contain all the different objects in your game control, and schedule their interactions.
 */
public class GameController {

    private Terrain mainTerrain;
    private Camera mainCamera;
    private SpritesController spritesController;

    /** Singleton pattern */
    private static GameController _instance;

    public static GameController getInstance(){
        return _instance;
    }

    public GameController() throws SlickException {
        mainTerrain = new Terrain();
        mainCamera = new Camera();
        spritesController = new SpritesController();
        _instance = this;
    }

    public Terrain getMainTerrain() {
        return mainTerrain;
    }

    public Camera getMainCamera() {
        return mainCamera;
    }

    public void init(GameContainer gc) throws SlickException {
        GameCoordinate.reset();
        spritesController.init();
        mainCamera.init();
    }

    public void update(Input input, int delta) throws SlickException {
        spritesController.update(input, delta);
        mainCamera.update(input, delta);
    }

    public void render(Graphics g) throws SlickException {
        mainTerrain.render(mainCamera);
        spritesController.render(mainCamera);

    }

}
