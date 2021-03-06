package shadowbuild.control;

import shadowbuild.main.App;
import shadowbuild.util.Vector2;

/**
 * This class defines static variable of the screen and world coordinate system
 * Screen coordinate system 1024 * 768
 * World coordinate system 1920 * 1920
 */
public class GameCoordinate {

    public static final int ORIGIN_X = 0;
    public static final int ORIGIN_Y = 0;
    public static final int SCREEN_WIDTH;
    public static final int SCREEN_HEIGHT;
    public static final int SCREEN_MIDDLE_X;
    public static final int SCREEN_MIDDLE_Y;
    public static final int WORLD_WIDTH;
    public static final int WORLD_HEIGHT;
    public static final int WORLD_MIDDLE_X;
    public static final int WORLD_MIDDLE_Y;

    static {
        SCREEN_WIDTH = App.WINDOW_WIDTH;
        SCREEN_HEIGHT = App.WINDOW_HEIGHT;
        SCREEN_MIDDLE_X = App.WINDOW_WIDTH/2;
        SCREEN_MIDDLE_Y = App.WINDOW_HEIGHT/2;
        WORLD_WIDTH = GameController.getMainTerrain().getWidth();
        WORLD_HEIGHT = GameController.getMainTerrain().getWidth();
        WORLD_MIDDLE_X = WORLD_WIDTH/2;
        WORLD_MIDDLE_Y = WORLD_HEIGHT/2;
    }

    /**
     * Convert a position from world coordinate system to screen coordinate system
     */
    public static Vector2 worldToScreen(Vector2 v) {
        return new Vector2(
            v.getX() - GameController.getMainCamera().getScope().getX(),
            v.getY() - GameController.getMainCamera().getScope().getY()
        );
    }

    /**
     * Convert a position from screen coordinate system to world coordinate system
     */
    public static Vector2 screenToWorld(Vector2 v) {
        return new Vector2(
            v.getX() + GameController.getMainCamera().getScope().getX(),
            v.getY() + GameController.getMainCamera().getScope().getY()
        );
    }

}
