package shadowbuild.camera;

import org.newdawn.slick.Input;
import shadowbuild.control.GameController;
import shadowbuild.control.GameCoordinate;
import shadowbuild.control.SpritesController;
import shadowbuild.helper.Logger;
import shadowbuild.main.App;
import shadowbuild.sprite.Selectable;
import shadowbuild.sprite.Sprite;
import shadowbuild.sprite.buildings.CommandCentre;
import shadowbuild.util.Rect;
import shadowbuild.util.Vector2;

/**
 * This class should be used to restrict the game's view to a subset of the entire control.
 */
public class Camera {

    /** The scope of the game's view */
    private Rect scope;
    /** The target the camera should follow */
    private Selectable followTarget;

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

    public boolean isFollow() {
        return followTarget != null;
    }

    public void follow(Selectable followTarget) {
        this.followTarget = followTarget;
    }

    public void unFollow() {
        this.followTarget = null;
    }

    public void move(Vector2 orientation, double distance) {
        scope.move(orientation, distance);
    }

    public void init() {
        for (Sprite commandCentre: SpritesController.getSprites(CommandCentre.class, GameController.getMainPlayer())) {
            setPos(commandCentre.getPos());
        }
    }

    public void update(Input input, int delta) {
        if (canAcceptInput()) {
            if(input.isKeyDown(Input.KEY_D)) {
                unFollow();
                scope.move(Vector2.RIGHT, delta * 0.5);
            }
            if(input.isKeyDown(Input.KEY_A)) {
                unFollow();
                scope.move(Vector2.LEFT, delta * 0.5);
            }
            if(input.isKeyDown(Input.KEY_W)) {
                unFollow();
                scope.move(Vector2.UP, delta * 0.5);
            }
            if(input.isKeyDown(Input.KEY_S)) {
                unFollow();
                scope.move(Vector2.DOWN, delta * 0.5);
            }
        }

        /** Move along with the followTarget */
        if (isFollow()){
            setPos(followTarget.getPos());
        }
        /** Restrict the camera movement */
        clamp();
    }

    private void clamp() {
        /** Stop when reach the left */
        if(scope.getX() < GameCoordinate.ORIGIN_X)
            scope.setX(GameCoordinate.ORIGIN_X);
        /** Stop when reach the right */
        else if(scope.getMaxX() > GameCoordinate.WORLD_WIDTH)
            scope.setMaxX(GameCoordinate.WORLD_WIDTH);
        /** Stop when reach the top */
        if(scope.getY() < GameCoordinate.ORIGIN_X)
            scope.setY(GameCoordinate.ORIGIN_X);
        /** Stop when reach the bottom */
        else if(scope.getMaxY() > GameCoordinate.WORLD_HEIGHT)
            scope.setMaxY(GameCoordinate.WORLD_HEIGHT);
    }

    private boolean canAcceptInput(){
        return !GameController.getGameUI().getTextbox().isAcceptingInput();
    }
}

