package shadowbuild.sprite;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import shadowbuild.camera.Camera;
import shadowbuild.control.GameController;
import shadowbuild.control.GameCoordinate;
import shadowbuild.sprite.constructable.Constructable;
import shadowbuild.util.*;

/**
 * More specific version of sprite
 * Define the region of the sprite and image source
 */
public abstract class RectSprite extends Sprite implements Renderable{
    /** The region of the sprite for collision detecting */
    private Rect region;
    /** The image to render */
    private Image image;

    public RectSprite(Image image) {
        this.image = image;
        region = new Rect(0, 0, image.getWidth(), image.getHeight());
    }

    public RectSprite(Vector2 pos, Image image) {
        this(image);
        setPos(pos);
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
        region.setWidth(image.getWidth());
        region.setHeight(image.getHeight());
    }

    /** render the rectSprite to the screen */
    @Override
    public void render(Camera camera, Graphics g) {
        /** only render if the image specified and inside the view of camera */
        if(image != null && region.collide(camera.getScope())) {
            Vector2 renderStart = GameCoordinate.worldToScreen(new Vector2(getPos().getX(),getPos().getY()));
            image.drawCentered((float) renderStart.getX(), (float)renderStart.getY());
            if (this instanceof Constructable && ((Sprite)this).getPlayer().equals(GameController.getMainPlayer())) {
                Constructable constructable = (Constructable)this;
                if (constructable.isConstructing()) {
                    double progress = constructable.getConstructMenu().getProgress();
                    g.setColor(Color.green);
                    g.fillRect((float) renderStart.getX() - 10, (float) renderStart.getY() - 15, (float)(20*progress), 3);
                }
            }
        }

    }

}
