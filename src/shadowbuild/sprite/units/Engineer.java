package shadowbuild.sprite.units;

import shadowbuild.control.GameController;
import shadowbuild.control.Input;
import shadowbuild.control.SpritesController;
import shadowbuild.helper.ResourceLoader;
import shadowbuild.sprite.Sprite;
import shadowbuild.sprite.buildings.CommandCentre;
import shadowbuild.sprite.resources.Metal;
import shadowbuild.sprite.resources.Resource;
import shadowbuild.util.*;

public class Engineer extends Unit{

    private static final String IMG_PATH = "assets/units/engineer.png";

    private static int mineRate = 2;
    private static int mineTime = 5000;
    private int load;

    private Resource mineTarget;


    public static void increaseMineRate(int num) {
        mineRate += num;
    }

    public Engineer(){
        super(ResourceLoader.readImage(IMG_PATH));
    }

    public Engineer(Vector2 pos) {
        super(pos, ResourceLoader.readImage(IMG_PATH));
    }

    public Boolean isMining() {
        return mineTarget != null;
    }

    @Override
    public void init() {
        setSpeed(0.1);
    }

    @Override
    public void update(Input input, int delta) {
        if(isSelected()) {
            if (input.isMouseRightPressed()) {
                /** Get the mouse input and convert to position in the world coordinate system */
                Vector2 destination = input.mousePos();
                Resource resource = detectResource(destination);
                if(resource != null) {
                    mineTarget = resource;
                    if(load > 0)
                        goToCentre();
                    else
                        goToMine(destination);
                }
                else {
                    mineTarget = null;
                    moveTowards(destination);
                }
            }
        }
    }

    private void goToMine(Vector2 destination) {
        moveTowards(destination);
        addTask((task, delta) -> {
            if(getPos().equals(destination)){
                mine();
            }
            task.stop();
        });
    }

    private void mine() {
        if (mineTarget == null || mineTarget.isEmpty()) {
            this.mineTarget = null;
            return;
        }
        setTask(null, mineTime);
        addTask((task, delta) -> {
            load += mineTarget.reduceCapacity(mineRate - load);
            if(mineTarget.isEmpty()) this.mineTarget = null;
            goToCentre();
            task.stop();
        });
    }

    private void goToCentre() {
        Vector2 nearestCentrePos = findNearestCentre().getPos();
        Vector2 destination = nearestCentrePos.subtract(getPos().orientation(nearestCentrePos).multiply(32));

        moveTowards(destination);
        addTask((task, delta) -> {
            if(getPos().equals(destination)) {
                if (mineTarget instanceof Metal)
                    getPlayer().getGameInfo().changeMetalAmount(load);
                else
                    getPlayer().getGameInfo().changeUnobtainiumAmount(load);
                load = 0;
                if(mineTarget == null || mineTarget.isEmpty()) {
                    this.mineTarget = null;
                    task.stop();
                } else
                    goToMine(mineTarget.getPos());
            }
            task.stop();
        });
    }

    private Resource detectResource(Vector2 pos) {
        for(Sprite sprite: SpritesController.getSprites("Metal")) {
            Resource resource = (Resource) sprite;
            if(resource.canTrigger(pos))
                return resource;
        }
        for(Sprite sprite: SpritesController.getSprites("Unobtainium")) {
            Resource resource = (Resource) sprite;
            if(resource.canTrigger(pos))
                return resource;
        }
        return null;
    }


    private CommandCentre findNearestCentre() {
        CommandCentre centre = null;
        double minDistance = Double.MAX_VALUE;
        for (Sprite sprite: SpritesController.getSprites("CommandCentre")) {
            Double distance = getPos().distance(sprite.getPos());
            if(distance < minDistance) {
                minDistance = distance;
                centre = (CommandCentre) sprite;
            }
        }
        return centre;
    }
}
