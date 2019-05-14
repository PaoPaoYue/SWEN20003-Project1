package shadowbuild.sprite.effects;

import shadowbuild.helper.ResourceLoader;
import shadowbuild.sprite.buildings.Building;

public class BuildingHighlight extends Highlight<Building> {

    private static final String IMG_PATH = "assets/highlight_large.png";

    public BuildingHighlight(Building highlightTarget) {
        super(highlightTarget, ResourceLoader.readImage(IMG_PATH));
    }

}
