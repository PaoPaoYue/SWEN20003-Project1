package shadowbuild.sprite.buildings;

import org.newdawn.slick.Input;

import shadowbuild.helper.ResourceLoader;
import shadowbuild.sprite.constructable.ConstructMenu;
import shadowbuild.sprite.constructable.ConstructMenuItem;
import shadowbuild.sprite.constructable.Constructable;
import shadowbuild.util.Vector2;

public class CommandCentre extends Building implements Constructable {

    private static final String IMG_PATH = "assets/buildings/command_centre.png";

    private static int scoutCost = -5;
    private static int builderCost = -10;
    private static int engineerCost = -20;
    private static int constructTime = 5000;

    private ConstructMenu constructMenu;

    public CommandCentre() {super(ResourceLoader.readImage(IMG_PATH));}

    public CommandCentre(Vector2 pos){
        super(pos, ResourceLoader.readImage(IMG_PATH));
    }

    @Override
    public void init() {
        constructMenu = new ConstructMenu(
                new ConstructMenuItem("Scout", scoutCost, 0, constructTime),
                new ConstructMenuItem("Builder", builderCost, 0, constructTime),
                new ConstructMenuItem("Engineer", engineerCost, 0, constructTime)
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
}
