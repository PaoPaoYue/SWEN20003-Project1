package shadowbuild.control;

import org.newdawn.slick.Graphics;
import shadowbuild.camera.Camera;
import shadowbuild.control.coroutine.Task;
import shadowbuild.helper.ResourceLoader;
import shadowbuild.player.Player;
import shadowbuild.sprite.Renderable;
import shadowbuild.sprite.Selectable;
import shadowbuild.sprite.Sprite;
import shadowbuild.sprite.buildings.Building;
import shadowbuild.sprite.effects.BuildingHighlight;
import shadowbuild.sprite.effects.UnitHighlight;
import shadowbuild.sprite.units.Unit;
import shadowbuild.util.Vector2;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is to contain all the sprites and control their behaviours
 */
public class SpritesController {

    private static final String SCV_PATH = "assets/objects.csv";

    /** The units which needs to follow */
    private Selectable selectedSprite;
    private Map<Player, Selectable> othersSelectedSprites;
    private Map<Class<? extends Sprite>, HashSet<Sprite>> sprites;
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

    public static void addSprite(Sprite sprite) {
        getInstance().spriteAddQueue.offer(sprite);
    }

    public static void addSprite(Sprite sprite, Player player) {
        sprite.setPlayer(player);
        getInstance().spriteAddQueue.offer(sprite);
    }

    public static void removeSprite(Sprite sprite) {
        getInstance().spriteRemoveQueue.offer(sprite);
    }

    public static Set<Sprite> getSprites(Class<? extends Sprite> spriteClass) {
        Set<Sprite> res = getInstance().sprites.get(spriteClass);
        if(res != null) return res;
        res = new HashSet<>();
        for (Map.Entry<Class<? extends Sprite>, HashSet<Sprite>> entry : getInstance().sprites.entrySet()) {
            if (spriteClass.isAssignableFrom(entry.getKey()))
                res.addAll(entry.getValue());
        }
        return res;

    }

    public static Set<Sprite> getSprites(Class<? extends Sprite> spriteClass, Player player) {
        return getSprites(spriteClass).stream().filter((item)-> item.getPlayer().equals(player)).collect(Collectors.toSet());
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
        List<List<Sprite>> initList = ResourceLoader.readCSV(SCV_PATH);
        int i = 0;
        for (Sprite sprite: initList.get(i++)) {
            addSprite(sprite);
        }
        List<Player> allPlayers = GameController.getAllPlayers();
        allPlayers.sort(Comparator.comparingInt(Player::getId));
        for (Player player: allPlayers) {
            for (Sprite sprite: initList.get(i++)) {
                addSprite(sprite, player);
            }
        }
        othersSelectedSprites = new HashMap<>();
        for (Player player: GameController.getOtherPlayers()){
            othersSelectedSprites.put(player, null);
        }
    }

    public void update(InputController mainInput, Map<Player, NetworkInputController> othersInputs, int delta) {
        removeSprites();
        addSprites();
        updateSelect(mainInput.getInput());
        updateTasks(delta);
        updateNonPlayerSprites(delta);
        updateSprites(GameController.getMainPlayer(), mainInput.getInput(), delta);
        for (Map.Entry<Player, NetworkInputController> entry: othersInputs.entrySet()) {
            updateOthersSelect(entry.getKey(), entry.getValue().getInput());
            updateSprites(entry.getKey(), entry.getValue().getInput(), delta);
        }
    }

    public void render(Camera camera, Graphics g) {
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
            sprite.render(camera, g);
        }
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

    private void removeSprites() {
        while (!spriteRemoveQueue.isEmpty()){
            Sprite sprite = spriteRemoveQueue.poll();
            Class<? extends Sprite> key = sprite.getClass();
            if (getInstance().sprites.containsKey(key)) {
                getInstance().sprites.get(key).remove(sprite);
            }
        }
    }

   private Selectable detectSelect(Vector2 pos, Player player) {
       Selectable selectTarget = null;
       for (Map.Entry<Class<? extends Sprite>, HashSet<Sprite>> entry : sprites.entrySet()) {
           if(Selectable.class.isAssignableFrom(entry.getKey())) {
               for (Sprite sprite: entry.getValue()) {
                   if ((sprite.getPlayer() == null || sprite.getPlayer().equals(player)) && ((Selectable)sprite).canSelect(pos)) {
                       selectTarget = ((Selectable)sprite);
                       if (sprite instanceof Unit)
                           return selectTarget;
                   }
               }
           }
       }
       return selectTarget;
   }

    private void updateSelect(Input input) {
        if (input.isMouseLeftPressed()) {
            Selectable selectTarget = detectSelect(input.mousePos(), GameController.getMainPlayer());
            if (selectTarget == null && selectedSprite != null) {
                selectedSprite.unselect();
                selectedSprite = null;
                GameController.getMainCamera().unFollow();
            }
            else if (selectTarget != selectedSprite){
                if(selectedSprite != null)
                    selectedSprite.unselect();
                selectTarget.select();
                selectedSprite = selectTarget;
                GameController.getMainCamera().follow(selectedSprite);
                if (selectedSprite instanceof Unit)
                    addSprite(new UnitHighlight((Unit)selectedSprite));
                else if (selectedSprite instanceof Building)
                    addSprite(new BuildingHighlight((Building)selectedSprite));
            }
        }
    }

    private void updateOthersSelect(Player player, Input input) {
        Selectable selectedSprite = othersSelectedSprites.get(player);
        if (input.isMouseLeftPressed()) {
            Selectable selectTarget = detectSelect(input.mousePos(), player);
            if (selectTarget == null && selectedSprite != null) {
                selectedSprite.unselect();
                othersSelectedSprites.put(player, null);
            }
            else if (selectTarget != selectedSprite){
                if(selectedSprite != null)
                    selectedSprite.unselect();
                othersSelectedSprites.put(player, selectTarget);
                selectTarget.select();
            }
        }
    }

    private void updateNonPlayerSprites(int delta) {
        for (HashSet<Sprite> set : sprites.values()) {
            for (Sprite sprite: set) {
                if (sprite.getPlayer() == null)
                    sprite.update(Input.NO_INPUT, delta);
            }
        }
    }



    private void updateSprites(Player player, Input input, int delta) {
        /** update all sprites */
        for (HashSet<Sprite> set : sprites.values()) {
            for (Sprite sprite: set) {
                if (sprite.getPlayer() != null && sprite.getPlayer().equals(player))
                sprite.update(input, delta);
            }
        }
    }

    private void updateTasks(int delta) {
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

}
