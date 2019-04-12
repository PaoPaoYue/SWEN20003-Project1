package shadowbuild.sprite;

import org.newdawn.slick.Input;
import shadowbuild.control.GameController;
import shadowbuild.control.coroutine.Task;
import shadowbuild.control.coroutine.TaskExecutor;
import shadowbuild.control.SpritesController;
import shadowbuild.control.coroutine.TimeLimitedTask;
import shadowbuild.terrain.Terrain;
import shadowbuild.util.*;

// basic sprite class
// define the position and movement
public abstract class Sprite {
    private Vector2 pos;

    public Sprite() {
        pos = new Vector2();
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public boolean move(Vector2 orientation, double distance) {
        Vector2 pos = getPos();
        pos.move(orientation, distance);
        if(GameController.getInstance().mainTerrain.collide(pos))
            return true;
        setPos(pos);
        return false;
    }

    public boolean moveTowards(Vector2 destination, double distance) {
        Vector2 pos = getPos();
        pos.moveTowards(destination, distance);
        if(GameController.getInstance().mainTerrain.collide(pos))
            return true;
        else if(getPos().equals(pos))
            return true;
        setPos(pos);
        return false;
    }

//    public boolean collide(Sprite sprite) {
//        return pos.collide(sprite.getPos());
//    }
//
//    public boolean collide(RectSprite sprite) {
//        return pos.collide(sprite.getRect());
//    }

    public boolean collide(Terrain terrain) {
        return terrain.collide(pos);
    }

    public abstract void init();

    public abstract void update(Input input, int delta);

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

    public void removeTask() {
        SpritesController.getInstance().removeTask(this);
    }

}
