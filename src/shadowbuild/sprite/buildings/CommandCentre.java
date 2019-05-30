package shadowbuild.sprite.buildings;

import org.newdawn.slick.Input;

import shadowbuild.helper.ResourceLoader;
import shadowbuild.sprite.Triggerable;
import shadowbuild.sprite.constructable.ConstructMenu;
import shadowbuild.sprite.constructable.ConstructMenuItem;
import shadowbuild.sprite.constructable.Constructable;
import shadowbuild.sprite.units.Builder;
import shadowbuild.sprite.units.Engineer;
import shadowbuild.sprite.units.Scout;
import shadowbuild.util.Vector2;

public class CommandCentre extends Building implements Constructable, Triggerable {

    private static final String IMG_PATH = "assets/buildings/command_centre.png";

    private static final int SCOUT_COST = -5;
    private static final int BUILDER_COST = -10;
    private static final int ENGINEER_COST = -20;
    private static final int CONSTRUCT_TIME = 5000;

    private ConstructMenu constructMenu;

    public CommandCentre() {super(ResourceLoader.readImage(IMG_PATH));}

    public CommandCentre(Vector2 pos){
        super(pos, ResourceLoader.readImage(IMG_PATH));
    }

    @Override
    public void init() {
        constructMenu = new ConstructMenu(
                new ConstructMenuItem(Scout.class, SCOUT_COST, 0, CONSTRUCT_TIME),
                new ConstructMenuItem(Builder.class, BUILDER_COST, 0, CONSTRUCT_TIME),
                new ConstructMenuItem(Engineer.class, ENGINEER_COST, 0, CONSTRUCT_TIME)
        );
    }

    @Override
    public void update(Input input, int delta) {
        if(isSelected() && !isConstructing()) {
            if(input.isKeyPressed(Input.KEY_1)) {
                construct(0,getPos(), false);
            }
            else if(input.isKeyPressed(Input.KEY_2)) {
                construct(1,getPos(), false);
            }
            else if(input.isKeyPressed(Input.KEY_3)) {
                construct(2,getPos(), false);
            }
        }
    }

    @Override
    public ConstructMenu getConstructMenu() {
        return constructMenu;
    }

    @Override
    public boolean isTriggered() {
        return false;
    }

    @Override
    public void trigger() { }
}
