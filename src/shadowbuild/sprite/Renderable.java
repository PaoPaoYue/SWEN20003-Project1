package shadowbuild.sprite;

import shadowbuild.camera.Camera;

public interface Renderable extends Comparable<Renderable>{

    @Override
    default int compareTo(Renderable o) {
        return this.getRenderLayer() - o.getRenderLayer();
    }

    int getRenderLayer();

    void render(Camera camera);
}
