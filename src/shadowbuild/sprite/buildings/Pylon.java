package shadowbuild.sprite.buildings;

import shadowbuild.control.Input;
import shadowbuild.helper.ResourceLoader;
import shadowbuild.sprite.Triggerable;
import shadowbuild.sprite.units.Engineer;
import shadowbuild.util.Vector2;

public class Pylon extends Building implements Triggerable {

    private static final String IMG_PATH = "assets/buildings/pylon.png";
    private static final String ACTIVE_IMG_PATH = "assets/buildings/pylon_active.png";

    private static int engineerCapacityBuff = 1;

    private boolean triggered = false;

    public Pylon() {
        super(ResourceLoader.readImage(IMG_PATH));
    }

    public Pylon(Vector2 pos){
        super(pos, ResourceLoader.readImage(IMG_PATH));
    }

    @Override
    public void init() {

    }

    @Override
    public void update(Input input, int delta) {

    }

    @Override
    public boolean isTriggered() {
        return triggered;
    }

    @Override
    public void trigger() {
        if (!triggered) {
            triggered = true;
            setImage(ResourceLoader.readImage(ACTIVE_IMG_PATH));
            Engineer.increaseMineRate(engineerCapacityBuff);
        }
    }
}
