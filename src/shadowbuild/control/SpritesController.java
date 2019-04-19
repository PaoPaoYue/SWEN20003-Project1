package shadowbuild.control;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import shadowbuild.camera.Camera;
import shadowbuild.control.coroutine.Task;
import shadowbuild.sprite.RectSprite;
import shadowbuild.sprite.Sprite;
import shadowbuild.sprite.player.Scout;

import java.util.*;

/**
 * This class is to contain all the sprites and control their behaviours
 */
public class SpritesController {

    /** The player which needs to follow */
    private Sprite mainPlayer;
    /** All sprites */
    private ArrayList<Sprite> sprites;
    /** All coroutine tasks */
    private HashMap<Sprite, Queue<Task>> tasks;

    /** Singleton pattern */
    private static SpritesController _instance;

    public static SpritesController getInstance(){
        return _instance;
    }

    public SpritesController() {
        sprites = new ArrayList<>();
        tasks = new HashMap<>();
        _instance = this;
    }

    public Sprite getMainPlayer() {
        return mainPlayer;
    }

    public void init() throws SlickException {
        /** instantiate all sprites */
        mainPlayer = new Scout();
        sprites.add(mainPlayer);
        /** call init function on all sprites */
        for (Sprite sprite: sprites) {
            sprite.init();
        }
    }

    public void update(Input input, int delta) {
        /** update all sprites */
        for (Sprite sprite: sprites) {
            sprite.update(input, delta);
        }
        /** update all coroutine tasks on each sprites */
        for(Map.Entry<Sprite, Queue<Task>> entry : tasks.entrySet()) {
            Sprite sprite = entry.getKey();
            Queue<Task> qp = entry.getValue();
            Task task = qp.peek();
            task.run(sprite, delta);
            if(task.expired())
                qp.poll();
            if(qp.isEmpty())
                clearTask(sprite);
        }
    }

    public void render(Camera camera) {
        /** render all rectSprites */
        for (Sprite sprite: sprites) {
            if (sprite instanceof RectSprite)
                ((RectSprite)sprite).render(camera);
        }
    }

    /** reset all the coroutine task on a specif sprite */
    public void setTask(Sprite sprite, Task task) {
        Queue<Task> qp = new LinkedList<>();
        qp.offer(task);
        tasks.put(sprite, qp);
    }

    /** add a task to the task queue of that sprite */
    public void addTask(Sprite sprite, Task task) {
        if(tasks.containsKey(sprite))
            tasks.get(sprite).offer(task);
        else
            setTask(sprite, task);
    }

    /** clear all the current task performed on that sprite */
    public void clearTask(Sprite sprite) {
        tasks.remove(sprite);
    }


}
