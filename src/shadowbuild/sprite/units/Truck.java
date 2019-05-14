package shadowbuild.sprite.units;

import org.newdawn.slick.Input;
import shadowbuild.control.GameCoordinate;
import shadowbuild.helper.ResourceLoader;
import shadowbuild.sprite.constructable.ConstructMenu;
import shadowbuild.sprite.constructable.ConstructMenuItem;
import shadowbuild.sprite.constructable.Constructable;
import shadowbuild.util.*;

public class Truck extends Unit implements Constructable {

    private static final String IMG_PATH = "assets/units/truck.png";

    private static int constructTime = 15000;

    private ConstructMenu constructMenu;

    public Truck(){
        super(ResourceLoader.readImage(IMG_PATH));
    }

    public Truck(Vector2 pos) {
        super(pos, ResourceLoader.readImage(IMG_PATH));
    }

    @Override
    public void init() {
        setSpeed(0.25);
        constructMenu = new ConstructMenu(
                new ConstructMenuItem("CommandCentre", 0, 0, constructTime)
        );
    }

    @Override
    public void update(Input input, int delta) {
        if(isSelected() && !isConstructing()) {
            if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
                /** Get the mouse input and convert to position in the world coordinate system */
                Vector2 destination = GameCoordinate.screenToWorld(new Vector2(input.getMouseX(), input.getMouseY()));
                /** Set a coroutine movement task */
                moveTowards(destination);
            }
            if(input.isKeyPressed(Input.KEY_1)) {
                construct(0, getPos());
            }
        }
    }

    @Override
    public ConstructMenu getConstructMenu() {
        return constructMenu;
    }

}
