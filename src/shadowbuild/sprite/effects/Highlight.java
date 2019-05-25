package shadowbuild.sprite.effects;

import org.newdawn.slick.Image;
import shadowbuild.control.Input;
import shadowbuild.sprite.RectSprite;
import shadowbuild.sprite.Selectable;

public abstract class Highlight<T extends Selectable> extends RectSprite {

    private static final int RENDERLAYER = -1;

    private T highlightTarget;

    public Highlight(T highlightTarget, Image image) {
        super(image);
        this.highlightTarget = highlightTarget;
    }

    @Override
    public int getRenderLayer() {
        return RENDERLAYER;
    }

    @Override
    public void init() {
        setPos(highlightTarget.getPos());
    }

    @Override
    public void update(Input input, int delta) {
        if(highlightTarget.isSelected()) {
            setPos(highlightTarget.getPos());
        } else {
            destroySelf();
        }
    }

}
