package shadowbuild.sprite.buildings;

import shadowbuild.control.Input;
import shadowbuild.helper.ResourceLoader;
import shadowbuild.sprite.constructable.ConstructMenu;
import shadowbuild.sprite.constructable.ConstructMenuItem;
import shadowbuild.sprite.constructable.Constructable;
import shadowbuild.sprite.units.Truck;
import shadowbuild.util.Vector2;

public class Factory extends Building implements Constructable {

    private static final String IMG_PATH = "assets/buildings/factory.png";

    private static int truckCost = -150;
    private static int constructTime = 5000;

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
                new ConstructMenuItem(Truck.class, truckCost, 0, constructTime)
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