package shadowbuild.sprite.buildings;

import org.newdawn.slick.Image;
import shadowbuild.control.SpritesController;
import shadowbuild.sprite.RectSprite;
import shadowbuild.sprite.Selectable;
import shadowbuild.sprite.effects.BuildingHighlight;
import shadowbuild.util.Vector2;

public abstract class Building extends RectSprite implements Selectable {

    private static final int RENDERLAYER = 0;

    private boolean selected = false;

    public Building(Image image) {
        super(image);
    }

    public Building(Vector2 pos, Image image) {
        super(pos, image);
    }

    @Override
    public int getRenderLayer() {
        return RENDERLAYER;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void select() {
        if (!selected) {
            selected = true;
        }
    }

    @Override
    public void unselect() {
        selected = false;
    }

    @Override
    public void destroySelf() {
        unselect();
        super.destroySelf();
    }
}
