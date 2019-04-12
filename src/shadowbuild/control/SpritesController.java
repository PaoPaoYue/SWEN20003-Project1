package shadowbuild.control;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import shadowbuild.camera.Camera;
import shadowbuild.control.coroutine.Task;
import shadowbuild.control.coroutine.TaskExecutor;
import shadowbuild.sprite.RectSprite;
import shadowbuild.sprite.Sprite;
import shadowbuild.sprite.player.Scout;

import java.util.*;

public class SpritesController {

    public Sprite mainPlayer;
    private ArrayList<Sprite> sprites;
    private HashMap<Sprite, Queue<Task>> tasks;

    private static SpritesController _instance;

    public static SpritesController getInstance(){
        return _instance;
    }

    public SpritesController() {
        sprites = new ArrayList<>();
        tasks = new HashMap<>();
        _instance = this;
    }

    public void init(GameContainer gc) throws SlickException {
        mainPlayer = new Scout(new Image("assets/scout.png"));
        sprites.add(mainPlayer);
        for (Sprite sprite: sprites) {
            sprite.init();
        }
    }

    public void update(Input input, int delta) {
        for (Sprite sprite: sprites) {
            sprite.update(input, delta);
        }
        for(Map.Entry<Sprite, Queue<Task>> entry : tasks.entrySet()) {
            Sprite sprite = entry.getKey();
            Queue<Task> qp = entry.getValue();
            Task task = qp.peek();
            task.run(sprite, delta);
            if(task.expired())
                qp.poll();
            if(qp.isEmpty())
                removeTask(sprite);

        }
    }

    public void render(Camera camera) {
//        sprites.sort(new Comparator<Sprite>() {
//            @Override
//            public int compare(Sprite o1, Sprite o2) {
//                int layer1 = o1 instanceof RectSprite? ((RectSprite)o1).getLayerIndex(): Integer.MIN_VALUE;
//                int layer2 = o2 instanceof RectSprite? ((RectSprite)o2).getLayerIndex(): Integer.MIN_VALUE;
//                if(layer2 - layer1 != 0){
//                    return layer2 - layer1;
//                }
//                else {
//                    return o2.getPos().subtract(o1.getPos()).getY() < 0? -1: 1;
//                }
//            }
//        });
        for (Sprite sprite: sprites) {
            if (sprite instanceof RectSprite)
                ((RectSprite)sprite).render(camera);
            else
                break;
        }
    }

    public void setTask(Sprite sprite, Task task) {
        Queue<Task> qp = new LinkedList<>();
        qp.offer(task);
        tasks.put(sprite, qp);
    }

    public void addTask(Sprite sprite, Task task) {
        if(tasks.containsKey(sprite))
            tasks.get(sprite).offer(task);
        else
            setTask(sprite, task);
    }

    public void removeTask(Sprite sprite) {
        tasks.remove(sprite);
    }


}
