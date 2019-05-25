package shadowbuild.sprite.resources;

import org.newdawn.slick.Image;
import shadowbuild.control.Input;
import shadowbuild.sprite.RectSprite;
import shadowbuild.sprite.Triggerable;
import shadowbuild.util.Vector2;

public abstract class Resource extends RectSprite implements Triggerable {

    private static final int RENDERLAYER = 0;

    private int capacity;

    public Resource(Image image, int maxCapacity) {
        super(image);
        capacity = maxCapacity;
    }

    public Resource(Vector2 pos, Image image, int maxCapacity) {
        super(pos, image);
        capacity = maxCapacity;
    }

    public boolean isEmpty() {return capacity == 0;}

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int reduceCapacity(int delta) {
        if(delta < 0) {
            return 0;
        }
        if(capacity - delta < 0) {
            int result = capacity;
            capacity = 0;
            return result;
        } else {
            capacity -= delta;
            return  delta;
        }
    }

    @Override
    public boolean isTriggered() {
        return false;
    }

    @Override
    public void trigger() {

    }

    @Override
    public int getRenderLayer() {
        return RENDERLAYER;
    }

    @Override
    public void init() {

    }

    @Override
    public void update(Input input, int delta) {
        if(capacity <= 0) destroySelf();
    }
}
