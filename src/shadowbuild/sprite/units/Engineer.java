package shadowbuild.sprite.units;

import org.newdawn.slick.Input;
import shadowbuild.control.GameController;
import shadowbuild.control.GameCoordinate;
import shadowbuild.control.SpritesController;
import shadowbuild.helper.ResourceLoader;
import shadowbuild.sprite.Sprite;
import shadowbuild.sprite.buildings.CommandCentre;
import shadowbuild.sprite.resources.Metal;
import shadowbuild.sprite.resources.Resource;
import shadowbuild.util.*;

public class Engineer extends Unit{

    private static final String IMG_PATH = "assets/units/engineer.png";

    private static final int MINE_TIME = 5000;

    private static int mineRate = 2;
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
            if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
                /** Get the mouse input and convert to position in the world coordinate system */
                Vector2 pos = GameCoordinate.screenToWorld(new Vector2(input.getMouseX(), input.getMouseY()));
                Resource resource = detectResource(pos);
                CommandCentre commandCentre = detectCommandCentre(pos);
                if(resource != null) {
                    mineTarget = resource;
                    if(load > 0)
                        goToCentre(findNearestCentre());
                    else
                        goToMine(resource);
                }
                else if (commandCentre != null) {
                    if (load > 0)
                        goToCentre(commandCentre);
                    else
                        moveTowards(pos);
                }
                else
                    moveTowards(pos);
            }
        }
    }

    private void goToMine(Resource mineTarget) {
        moveTowards(mineTarget.getPos());
        addTask((task, delta) -> {
            if(getPos().equals(mineTarget.getPos())){
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
        setTask(null, MINE_TIME);
        addTask((task, delta) -> {
            load += mineTarget.reduceCapacity(mineRate - load);
            if(mineTarget.isEmpty()) this.mineTarget = null;
            goToCentre(findNearestCentre());
            task.stop();
        });
    }

    private void goToCentre(CommandCentre commandCentre) {
        Vector2 nearestCentrePos = commandCentre.getPos();
        Vector2 destination = nearestCentrePos.subtract(getPos().orientation(nearestCentrePos).multiply(32));

        moveTowards(destination);
        addTask((task, delta) -> {
            if(getPos().equals(destination)) {
                if (mineTarget instanceof Metal)
                    GameController.getMainPlayer().getInfo().changeMetalAmount(load);
                else
                    GameController.getMainPlayer().getInfo().changeUnobtainiumAmount(load);
                load = 0;
                if(mineTarget == null || mineTarget.isEmpty()) {
                    this.mineTarget = null;
                    task.stop();
                } else
                    goToMine(mineTarget);
            }
            task.stop();
        });
    }

    private CommandCentre detectCommandCentre(Vector2 pos) {
        for(Sprite sprite: SpritesController.getSprites(CommandCentre.class)) {
            CommandCentre commandCentre = (CommandCentre) sprite;
            if(commandCentre.canTrigger(pos))
                return commandCentre;
        }
        return null;
    }

    private Resource detectResource(Vector2 pos) {
        for(Sprite sprite: SpritesController.getSprites(Resource.class)) {
            Resource resource = (Resource) sprite;
            if(resource.canTrigger(pos))
                return resource;
        }
        return null;
    }


    private CommandCentre findNearestCentre() {
        CommandCentre centre = null;
        double minDistance = Double.MAX_VALUE;
        for (Sprite sprite: SpritesController.getSprites(CommandCentre.class)) {
            Double distance = getPos().distance(sprite.getPos());
            if(distance < minDistance) {
                minDistance = distance;
                centre = (CommandCentre) sprite;
            }
        }
        return centre;
    }
}
