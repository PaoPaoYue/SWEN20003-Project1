package shadowbuild.control;

import shadowbuild.main.App;
import shadowbuild.util.Vector2;

public class GameCoordinate {
    public static final int ORIGINX = 0;
    public static final int ORIGINY = 0;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int SCREEN_MIDDLE_X;
    public static int SCREEN_MIDDLE_Y;
    public static int WORLD_WIDTH;
    public static int WORLD_HEIGHT;
    public static int WORLD_MIDDLE_X;
    public static int WORLD_MIDDLE_Y;

    public static void reset() {
        SCREEN_WIDTH = App.WINDOW_WIDTH;
        SCREEN_HEIGHT = App.WINDOW_HEIGHT;
        SCREEN_MIDDLE_X = App.WINDOW_WIDTH/2;
        SCREEN_MIDDLE_Y = App.WINDOW_HEIGHT/2;
        WORLD_WIDTH = GameController.getInstance().mainTerrain.getWidth();
        WORLD_HEIGHT = GameController.getInstance().mainTerrain.getWidth();
        WORLD_MIDDLE_X = WORLD_WIDTH/2;
        WORLD_MIDDLE_Y = WORLD_HEIGHT/2;
    }

    public static Vector2 worldToScreen(Vector2 v) {
        return new Vector2(
            v.getX() - GameController.getInstance().mainCamera.getScope().getX(),
            v.getY() - GameController.getInstance().mainCamera.getScope().getY()
        );
    }

    public static Vector2 screenToWorld(Vector2 v) {
        return new Vector2(
            v.getX() + GameController.getInstance().mainCamera.getScope().getX(),
            v.getY() + GameController.getInstance().mainCamera.getScope().getY()
        );
    }

}
