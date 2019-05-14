package shadowbuild.sprite.units;

import org.newdawn.slick.Image;
import shadowbuild.control.GameController;
import shadowbuild.control.SpritesController;
import shadowbuild.sprite.*;
import shadowbuild.sprite.effects.UnitHighlight;
import shadowbuild.util.Vector2;

import java.util.Set;

public abstract class Unit extends RectSprite implements Selectable, Movable {

    private static final int RENDERLAYER = 5;

    private boolean selected = false;
    private double speed = 0.1;

    public Unit(Image image) {
        super(image);
    }

    public Unit(Vector2 pos, Image image) {
        super(pos, image);
    }

    @Override
    public int getRenderLayer() {
        return RENDERLAYER;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void move(Vector2 orientation) {
        setTask((task, coroutineDelta) -> {
            Vector2 pos = getPos().move(orientation, speed * coroutineDelta);
            if(canMove(pos)) {
                setPos(pos);
                triggerPylon();
            } else
                task.stop();
        });
    }

    @Override
    public void moveTowards(Vector2 destination) {
        setTask((task, coroutineDelta) -> {
            Vector2 pos = getPos().moveTowards(destination, speed * coroutineDelta);
            if(canMove(pos)) {
                setPos(pos);
                triggerPylon();
            } else
                task.stop();
        });
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void select() {
        if (!selected) {
            selected = true;
            if(SpritesController.getSelectedSprite() != null)
                SpritesController.getSelectedSprite().unselect();
            SpritesController.setSelectedSprite(this);
            GameController.getMainCamera().follow(this);
            SpritesController.addSprite(new UnitHighlight(this));
        }
    }

    @Override
    public void unselect() {
        if (selected){
            selected = false;
            SpritesController.setSelectedSprite(null);
            GameController.getMainCamera().unFollow();
        }
    }

    @Override
    public void destroySelf() {
        unselect();
        super.destroySelf();
    }

    private void triggerPylon() {
        Set<Sprite> pylons = SpritesController.getSprites("Pylon");
        for (Sprite pylon : pylons) {
            Triggerable triggerable = (Triggerable)pylon;
            if(triggerable.canTrigger(getPos()) && !triggerable.isTriggered()) {
                triggerable.trigger();
            }
        }
    }
}
