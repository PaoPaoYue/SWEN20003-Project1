package shadowbuild.sprite.effects;

import shadowbuild.helper.ResourceLoader;
import shadowbuild.sprite.units.Unit;

public class UnitHighlight extends Highlight<Unit> {

    private static final String IMG_PATH = "assets/highlight.png";

    public UnitHighlight(Unit highlightTarget) {
        super(highlightTarget, ResourceLoader.readImage(IMG_PATH));
    }

}
