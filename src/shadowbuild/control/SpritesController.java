package shadowbuild.control;

import org.newdawn.slick.Input;
import shadowbuild.camera.Camera;
import shadowbuild.control.coroutine.Task;
import shadowbuild.helper.ResourceLoader;
import shadowbuild.sprite.Renderable;
import shadowbuild.sprite.Selectable;
import shadowbuild.sprite.Sprite;
import shadowbuild.sprite.SpritesParser;
import shadowbuild.sprite.units.Unit;
import shadowbuild.util.Vector2;

import java.util.*;

/**
 * This class is to contain all the sprites and control their behaviours
 */
public class SpritesController {

    private static final String SCV_PATH = "assets/objects.csv";

    /** The units which needs to follow */
    private Selectable selectedSprite;
    /** All sprites */
    private Map<Class<? extends Sprite>, HashSet<Sprite>> sprites;
    /** All coroutine tasks */
    private Map<Sprite, Queue<Task>> tasks;
    private Queue<Sprite> spriteAddQueue;
    private Queue<Sprite> spriteRemoveQueue;

    /** Singleton pattern */
    private static SpritesController instance;

    public static SpritesController getInstance(){
        if (instance == null) {
            instance = new SpritesController();
        }
        return instance;
    }

    private SpritesController() {
        sprites = new HashMap<>();
        tasks = new HashMap<>();
        spriteAddQueue = new LinkedList<>();
        spriteRemoveQueue = new LinkedList<>();
    }

    public static Selectable getSelectedSprite() {
        return getInstance().selectedSprite;
    }

    public static void setSelectedSprite(Selectable selectedSprite) {
        getInstance().selectedSprite = selectedSprite;
    }

    public static void addSprite(Sprite sprite) {
        getInstance().spriteAddQueue.offer(sprite);
    }

    private void addSprites() {
        while(!spriteAddQueue.isEmpty()) {
            Sprite sprite = spriteAddQueue.poll();
            Class<? extends Sprite> key = sprite.getClass();
            if (getInstance().sprites.containsKey(key)) {
                getInstance().sprites.get(key).add(sprite);
            } else {
                HashSet<Sprite> set = new HashSet<>();
                set.add(sprite);
                getInstance().sprites.put(key, set);
            }
            sprite.init();
        }
    }

    public static void removeSprite(Sprite sprite) {
        getInstance().spriteRemoveQueue.offer(sprite);
    }

    private void removeSprites() {
        while (!spriteRemoveQueue.isEmpty()){
            Sprite sprite = spriteRemoveQueue.poll();
            Class<? extends Sprite> key = sprite.getClass();
            if (getInstance().sprites.containsKey(key)) {
                getInstance().sprites.get(key).remove(sprite);
            }
        }
    }

    public static Set<Sprite> getSprites(String spriteClassName) {
        return getInstance().sprites.get(SpritesParser.getSpriteClass(spriteClassName));
    }

    /** reset all the coroutine task on a specif sprite */
    public static void setTask(Sprite sprite, Task task) {
        Queue<Task> qp = new LinkedList<>();
        qp.offer(task);
        getInstance().tasks.put(sprite, qp);
    }

    /** add a task to the task queue of that sprite */
    public static void addTask(Sprite sprite, Task task) {
        if(getInstance().tasks.containsKey(sprite)) {
            getInstance().tasks.get(sprite).offer(task);
        }
        else
            setTask(sprite, task);
    }

    /** clear all the current task performed on that sprite */
    public static void clearTask(Sprite sprite) {
        getInstance().tasks.remove(sprite);
    }

    public void init(){
        for (Sprite sprite: ResourceLoader.readCSV(SCV_PATH)) {
            addSprite(sprite);
        }
    }

    public void update(Input input, int delta) {

        addSprites();
        removeSprites();

        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            Vector2 mousePos = new Vector2(input.getMouseX(), input.getMouseY());
            Selectable selectTarget = detectSelect(GameCoordinate.screenToWorld(mousePos));
            if (selectTarget == null && selectedSprite != null) {
                selectedSprite.unselect();
            }
            else if (selectTarget != selectedSprite){
                selectTarget.select();
            }
        }
        /** update all sprites */
        for (HashSet<Sprite> set : sprites.values()) {
            for (Sprite sprite: set) {
                sprite.update(input, delta);
            }
        }
        /** update all coroutine tasks on each sprites */
        List<Sprite> clearTaskList = new ArrayList<>();
        for(Map.Entry<Sprite, Queue<Task>> entry : tasks.entrySet()) {
            Sprite sprite = entry.getKey();
            Queue<Task> qp = entry.getValue();
            if(qp.isEmpty()) {
                clearTaskList.add(sprite);
                continue;
            }
            Task task = qp.peek();
            task.run(sprite, delta);
            if(task.isExpired() && task.equals(qp.peek())) {
                qp.poll();
            }
        }
        for (Sprite sprite: clearTaskList) {
            clearTask(sprite);
        }
    }

    public void render(Camera camera) {
        /** render all rectSprites */
        List<Renderable> renderQueue = new ArrayList<>();
        for (Map.Entry<Class<? extends Sprite>, HashSet<Sprite>> entry : sprites.entrySet()) {
            if(Renderable.class.isAssignableFrom(entry.getKey())) {
                for (Sprite sprite: entry.getValue()) {
                    renderQueue.add((Renderable) sprite);
                }
            }
        }
        Collections.sort(renderQueue);
        for (Renderable sprite: renderQueue) {
            sprite.render(camera);
        }
    }

    private Selectable detectSelect(Vector2 pos) {
        Selectable selectTarget = null;
        for (Map.Entry<Class<? extends Sprite>, HashSet<Sprite>> entry : sprites.entrySet()) {
            if(Selectable.class.isAssignableFrom(entry.getKey())) {
                for (Sprite sprite: entry.getValue()) {
                    if (((Selectable)sprite).canSelect(pos)) {
                        selectTarget = ((Selectable)sprite);
                        if (sprite instanceof Unit)
                            return selectTarget;
                    }
                }
            }
        }
        return selectTarget;
    }

}
