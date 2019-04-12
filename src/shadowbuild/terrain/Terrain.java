package shadowbuild.terrain;

import org.newdawn.slick.tiled.TileSet;
import org.newdawn.slick.tiled.TiledMap;
import shadowbuild.camera.Camera;
import shadowbuild.util.Rect;
import shadowbuild.util.Vector2;

import java.util.ArrayList;

public class Terrain {

    private TiledMap mainMap;
    private int width;
    private int height;


    public Terrain(TiledMap map){
        mainMap = map;
        width = map.getWidth() * map.getTileWidth();
        height = map.getHeight() * map.getTileHeight();
    }

    public TiledMap getMainMap() {
        return mainMap;
    }

    public void setMainMap(TiledMap mainMap) {
        this.mainMap = mainMap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean collide(Vector2 pos) {
        if(!pos.collide(new Rect(0, 0, width, height)))
            return false;
        int x = (int)(pos.getX() / mainMap.getTileWidth());
        int y = (int)(pos.getY() / mainMap.getTileHeight());
        int blockId = mainMap.getTileId(x, y, 0);
        if(mainMap.getTileSet(0).getProperties(blockId).getProperty("solid").equals("true"))
            return true;
        return false;
    }

    public void render(Camera camera) {
        Rect scope = camera.getScope();
        int sx = (int)(scope.getX() / mainMap.getTileWidth());
        int sy = (int)(scope.getY() / mainMap.getTileHeight());
        int xtiles = (int)(scope.getWidth() / mainMap.getTileWidth())+1;
        int ytiles = (int)(scope.getWidth() / mainMap.getTileWidth())+1;
        int x = (int)(sx * mainMap.getTileWidth() - scope.getX());
        int y = (int)(sy * mainMap.getTileHeight() - scope.getY());
        mainMap.render(x,y,sx,sy,xtiles,ytiles);
    }

}
