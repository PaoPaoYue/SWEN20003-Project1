package shadowbuild.sprite.resources;

import shadowbuild.helper.ResourceLoader;
import shadowbuild.util.Vector2;

public class Metal extends Resource {

    private static final String IMG_PATH = "assets/resources/metal_mine.png";

    private static final int MAX_CAP = 500;

    public Metal() {
        super(ResourceLoader.readImage(IMG_PATH), MAX_CAP);
    }

    public Metal(Vector2 pos){
        super(pos, ResourceLoader.readImage(IMG_PATH), MAX_CAP);
    }

}
