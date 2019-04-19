package shadowbuild.sprite;

import org.newdawn.slick.Input;
import shadowbuild.control.GameController;
import shadowbuild.control.coroutine.Task;
import shadowbuild.control.coroutine.TaskExecutor;
import shadowbuild.control.SpritesController;
import shadowbuild.control.coroutine.TimeLimitedTask;
import shadowbuild.terrain.Terrain;
import shadowbuild.util.*;

/**
 * Basic sprite class
 * Define the position and movement
 */
public abstract class Sprite {
    private Vector2 pos;

    public Sprite() {
        pos = new Vector2();
    }

    public Sprite(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    /**
     * Move the sprite a certain distance in that direction
     * Return false if counter obstacle and otherwise return true
     */
    public boolean move(Vector2 orientation, double distance) {
        Vector2 pos = getPos().move(orientation, distance);
        if(GameController.getInstance().getMainTerrain().collide(pos))
            return false;
        setPos(pos);
        return true;
    }

    /**
     * Move the sprite a certain distance towards a destination
     * Return false if counter obstacle or reach the destination and otherwise return true
     */
    public boolean moveTowards(Vector2 destination, double distance) {
        Vector2 pos = getPos().moveTowards(destination, distance);
        if(GameController.getInstance().getMainTerrain().collide(pos))
            return false;
        else if(getPos().equals(pos))
            return false;
        setPos(pos);
        return true;
    }

//    public boolean collide(Sprite sprite) {
//        return pos.collide(sprite.getPos());
//    }
//
//    public boolean collide(RectSprite sprite) {
//        return pos.collide(sprite.getRect());
//    }

    /** Detect collision with the main terrain */
    public boolean collide(Terrain terrain) {
        return terrain.collide(pos);
    }

    /**
     * Every subclass of Sprite should override these two methods
     * To specify their own behaviour
     */
    public abstract void init();

    public abstract void update(Input input, int delta);

    /**
     * There methods can add a coroutine task which performs later than the update method
     * Alternative time parameter to set a time limit of the task
     */
    public void setTask(TaskExecutor executor) {
        SpritesController.getInstance().setTask(this, new Task(executor));
    }

    public void setTask(TaskExecutor executor, int time) {
        SpritesController.getInstance().setTask(this, new TimeLimitedTask(executor, time));
    }

    public void addTask(TaskExecutor executor) {
        SpritesController.getInstance().addTask(this, new Task(executor));
    }

    public void addTask(TaskExecutor executor, int time) {
        SpritesController.getInstance().addTask(this, new TimeLimitedTask(executor, time));
    }

    public void clearTask() {
        SpritesController.getInstance().clearTask(this);
    }

}
