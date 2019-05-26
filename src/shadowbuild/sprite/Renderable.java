package shadowbuild.sprite;

import org.newdawn.slick.Graphics;
import shadowbuild.camera.Camera;


public interface Renderable extends Comparable<Renderable>{

    @Override
    default int compareTo(Renderable o) {
        return this.getRenderLayer() - o.getRenderLayer();
    }

    int getRenderLayer();

    void render(Camera camera, Graphics g);
}
