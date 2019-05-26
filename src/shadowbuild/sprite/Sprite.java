package shadowbuild.sprite;

import shadowbuild.control.Input;
import shadowbuild.control.coroutine.Task;
import shadowbuild.control.coroutine.TaskExecutor;
import shadowbuild.control.SpritesController;
import shadowbuild.control.coroutine.TimeLimitedTask;
import shadowbuild.player.Player;
import shadowbuild.util.*;

/**
 * Basic sprite class
 * Define the position and movement
 */
public abstract class Sprite {
    private Vector2 pos;
    private Player player;

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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void destroySelf() {
        SpritesController.removeSprite(this);
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
        SpritesController.setTask(this, new Task(executor));
    }

    public void setTask(TaskExecutor executor, int time) {
        SpritesController.setTask(this, new TimeLimitedTask(executor, time));
    }

    public void addTask(TaskExecutor executor) {
        SpritesController.addTask(this, new Task(executor));
    }

    public void addTask(TaskExecutor executor, int time) {
        SpritesController.addTask(this, new TimeLimitedTask(executor, time));
    }

    public void clearTask() {
        SpritesController.clearTask(this);
    }
}
