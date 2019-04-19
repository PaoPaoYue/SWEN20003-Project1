package shadowbuild.terrain;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import shadowbuild.camera.Camera;
import shadowbuild.util.Rect;
import shadowbuild.util.Vector2;

/**
 * This class maintains a tiledMap and control its behaviour
 */
public class Terrain {

    private static String MAP_PATH = "assets/main.tmx";

    private TiledMap mainMap;
    /** width measured in pixels */
    private int width;
    /** height measured in pixels */
    private int height;


    public Terrain() throws SlickException {
        mainMap = new TiledMap(MAP_PATH);
        width = mainMap.getWidth() * mainMap.getTileWidth();
        height = mainMap.getHeight() * mainMap.getTileHeight();
    }

    public TiledMap getMainMap() {
        return mainMap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /** Detect if the position is on a solid tile */
    public boolean collide(Vector2 pos) {
        if(!pos.collide(new Rect(0, 0, width, height)))
            return false;
        int x = (int)(pos.getX() / mainMap.getTileWidth());
        int y = (int)(pos.getY() / mainMap.getTileHeight());
        int blockId = mainMap.getTileId(x, y, 0);
        if(mainMap.getTileProperty(blockId,"solid","none").equals("true"))
            return true;
        return false;
    }

    /** Render the tiledMap to screen */
    public void render(Camera camera) {
        Rect scope = camera.getScope();
        /** The top-left tile's position to be rendered */
        int sx = (int)(scope.getX() / mainMap.getTileWidth());
        int sy = (int)(scope.getY() / mainMap.getTileHeight());
        /** Numbers of tiles to be rendered */
        int xTiles = (int)(scope.getWidth() / mainMap.getTileWidth())+1;
        int yTiles = (int)(scope.getWidth() / mainMap.getTileWidth())+1;
        /** screen position to start render the map */
        int x = (int)(sx * mainMap.getTileWidth() - scope.getX());
        int y = (int)(sy * mainMap.getTileHeight() - scope.getY());
        mainMap.render(x,y,sx,sy,xTiles,yTiles);
    }

}
