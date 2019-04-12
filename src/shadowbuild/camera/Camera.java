package shadowbuild.camera;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import shadowbuild.control.GameController;
import shadowbuild.control.GameCoordinate;
import shadowbuild.control.SpritesController;
import shadowbuild.main.App;
import shadowbuild.util.Rect;
import shadowbuild.util.Vector2;

/**
 * This class should be used to restrict the game's view to a subset of the entire control.
 *
 * You are free to make ANY modifications you see fit.
 * These classes are provided simply as a starting point. You are not strictly required to use them.
 */
public class Camera {

    private Rect scope;

    public Camera() {
        scope = new Rect(0, 0, App.WINDOW_WIDTH, App.WINDOW_HEIGHT);
    }

    public Rect getScope() {
        return scope;
    }

    public void setScope(Rect scope) {
        this.scope = scope;
    }

    public Vector2 getPos() {return scope.getPos();}

    public void setPos(Vector2 pos) {
        scope.setPos(pos);
    }

    public void move(Vector2 orientation, double distance) {
        scope.move(orientation, distance);
    }

    private void clamp() {
        double x = 0;
        double y = 0;
        if(scope.getX() < 0)
            x = GameCoordinate.SCREEN_MIDDLE_X;
        else if(scope.getMaxX() > GameCoordinate.WORLD_WIDTH)
            x = GameCoordinate.WORLD_WIDTH - GameCoordinate.SCREEN_MIDDLE_X;
        if(scope.getY() < 0)
            y = GameCoordinate.SCREEN_MIDDLE_Y;
        else if(scope.getMaxY() > GameCoordinate.WORLD_HEIGHT)
            y = GameCoordinate.WORLD_HEIGHT - GameCoordinate.SCREEN_MIDDLE_Y;
        if(x != 0 && y != 0)
            setPos(new Vector2(x, y));
        else if(x != 0)
            setPos(new Vector2(x, scope.getPos().getY()));
        else if(y != 0)
            setPos(new Vector2(scope.getPos().getX(), y));
    }

    public void init(GameContainer gc) {
        setPos(new Vector2(GameCoordinate.WORLD_MIDDLE_X, GameCoordinate.WORLD_MIDDLE_Y));
    }

    public void update(Input input, int delta) {
//        if(App.DEBUG) {
//            if(input.isKeyDown(Input.KEY_RIGHT)) {
//                scope.move(Vector2.RIGHT, delta * 0.5);
//            }
//            if(input.isKeyDown(Input.KEY_LEFT)) {
//                scope.move(Vector2.LEFT, delta * 0.5);
//            }
//            if(input.isKeyDown(Input.KEY_UP)) {
//                scope.move(Vector2.UP, delta * 0.5);
//            }
//            if(input.isKeyDown(Input.KEY_DOWN)) {
//                scope.move(Vector2.DOWN, delta * 0.5);
//            }
//        }
        setPos(SpritesController.getInstance().mainPlayer.getPos());
        clamp();
    }



}
