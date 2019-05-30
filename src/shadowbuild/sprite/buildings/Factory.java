package shadowbuild.sprite.buildings;

import org.newdawn.slick.Input;
import shadowbuild.helper.ResourceLoader;
import shadowbuild.sprite.constructable.ConstructMenu;
import shadowbuild.sprite.constructable.ConstructMenuItem;
import shadowbuild.sprite.constructable.Constructable;
import shadowbuild.sprite.units.Truck;
import shadowbuild.util.Vector2;

public class Factory extends Building implements Constructable {

    private static final String IMG_PATH = "assets/buildings/factory.png";

    private static final int TRUCK_COST = -150;
    private static final int CONSTRUCT_TIME = 5000;

    private ConstructMenu constructMenu;

    public Factory() {
        super(ResourceLoader.readImage(IMG_PATH));
    }

    public Factory(Vector2 pos){
        super(pos, ResourceLoader.readImage(IMG_PATH));
    }

    @Override
    public void init() {
        constructMenu = new ConstructMenu(
                new ConstructMenuItem(Truck.class, TRUCK_COST, 0, CONSTRUCT_TIME)
        );
    }

    @Override
    public void update(Input input, int delta) {
        if(isSelected() && !isConstructing()) {
            if(input.isKeyPressed(Input.KEY_1)) {
                construct(0, getPos(), false);
            }
        }
    }


    @Override
    public ConstructMenu getConstructMenu() {
        return constructMenu;
    }
}