package shadowbuild.control;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.Log;
import shadowbuild.camera.Camera;
import shadowbuild.sprite.RectSprite;
import shadowbuild.sprite.Sprite;
import shadowbuild.terrain.Terrain;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * This class should be used to contain all the different objects in your game control, and schedule their interactions.
 *
 * You are free to make ANY modifications you see fit.
 * These classes are provided simply as a starting point. You are not strictly required to use them.
 */
public class GameController {

    public Terrain mainTerrain;
    public Camera mainCamera;
    public SpritesController spritesController;

    private static GameController _instance;

    public static GameController getInstance(){
        return _instance;
    }

    public GameController() throws SlickException {
        mainTerrain = new Terrain(new TiledMap("assets/main.tmx"));
        mainCamera = new Camera();
        spritesController = new SpritesController();
        _instance = this;
    }

    public void init(GameContainer gc) throws SlickException {
        spritesController.init(gc);
        mainCamera.init(gc);
        GameCoordinate.reset();
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
