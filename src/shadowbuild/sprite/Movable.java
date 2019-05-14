package shadowbuild.sprite;

import shadowbuild.control.GameController;
import shadowbuild.util.Vector2;

public interface Movable{

    Vector2 getPos();

    void setPos(Vector2 pos);

    double getSpeed();

    void setSpeed(double speed);

    default boolean canMove(Vector2 pos) {
        return !GameController.getMainTerrain().collide(pos) &&
            !getPos().equals(pos);
    }

    /**
     * Move the sprite a certain distance in that direction
     * Return false if counter obstacle and otherwise return true
     */
    void move(Vector2 orientation);

    /**
     * Move the sprite a certain distance towards a destination
     * Return false if counter obstacle or reach the destination and otherwise return true
     */
    void moveTowards(Vector2 destination);

}
