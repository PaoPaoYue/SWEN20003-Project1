package shadowbuild.sprite.resources;

import shadowbuild.helper.ResourceLoader;
import shadowbuild.util.Vector2;

public class Unobtainium extends Resource {

    private static final String IMG_PATH = "assets/resources/unobtainium_mine.png";

    private static int maxCapacity = 50;

    public Unobtainium() {
        super(ResourceLoader.readImage(IMG_PATH), maxCapacity);
    }

    public Unobtainium(Vector2 pos){
        super(pos, ResourceLoader.readImage(IMG_PATH), maxCapacity);
    }

}