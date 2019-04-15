package shadowbuild.sprite;

import org.lwjgl.Sys;
import org.newdawn.slick.Image;
import shadowbuild.camera.Camera;
import shadowbuild.control.GameController;
import shadowbuild.control.GameCoordinate;
import shadowbuild.util.*;

// more specific version of sprite
// define the region of the sprite and image source
public abstract class RectSprite extends Sprite{
    Rect region;
    Image image;
    int layerIndex;

    public RectSprite() {
        super();
        region = new Rect();
    }

    public RectSprite(Image image) {
        super();
        this.image = image;
        region = new Rect(0, 0, image.getWidth(), image.getHeight());
    }

    public RectSprite(Vector2 pos, Image image) {
        this.image = image;
        region = new Rect(0, 0, image.getWidth(), image.getHeight());
    }

    @Override
    public Vector2 getPos() {
        return region.getPos();
    }

    @Override
    public void setPos(Vector2 pos) {
        region.setPos(pos);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

//    public int getLayerIndex() {
//        return layerIndex;
//    }
//
//    public void setLayerIndex(int layerIndex) {
//        this.layerIndex = layerIndex;
//    }

//    @Override
//    public boolean collide(Sprite sprite) {
//        return region.collide(sprite.getPos());
//    }
//
//    @Override
//    public boolean collide(RectSprite sprite) {
//        return region.collide(sprite.getRect());
//    }

    public void render(Camera camera) {
        if(image != null && region.collide(camera.getScope())) {
            Vector2 renderStart = GameCoordinate.worldToScreen(new Vector2(
            getPos().getX() - image.getWidth()/2,getPos().getY() - image.getHeight()/2
            ));
            image.draw((float) renderStart.getX(), (float)renderStart.getY());
        }
    }

}
