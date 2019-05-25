package shadowbuild.sprite.units;

import shadowbuild.control.Input;
import shadowbuild.helper.ResourceLoader;
import shadowbuild.util.*;

public class Scout extends Unit{

    private static final String IMG_PATH = "assets/units/scout.png";

    public Scout(){
        super(ResourceLoader.readImage(IMG_PATH));
    }

    public Scout(Vector2 pos) {
        super(pos, ResourceLoader.readImage(IMG_PATH));
    }

    @Override
    public void init() {
        setSpeed(0.3);
    }

    @Override
    public void update(Input input, int delta) {
        if(isSelected()) {
            if (input.isMouseRightPressed()) {
                /** Get the mouse input and convert to position in the world coordinate system */
                Vector2 destination = input.mousePos();
                /** Set a coroutine movement task */
                moveTowards(destination);
            }
        }
    }
}
