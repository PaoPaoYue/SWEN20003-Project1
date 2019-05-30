package shadowbuild.helper;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import shadowbuild.sprite.Sprite;
import shadowbuild.sprite.buildings.CommandCentre;
import shadowbuild.sprite.buildings.Factory;
import shadowbuild.sprite.buildings.Pylon;
import shadowbuild.sprite.resources.Metal;
import shadowbuild.sprite.resources.Unobtainium;
import shadowbuild.sprite.units.Builder;
import shadowbuild.sprite.units.Engineer;
import shadowbuild.sprite.units.Scout;
import shadowbuild.sprite.units.Truck;
import shadowbuild.util.Vector2;

import java.util.*;

public class ResourceLoader {

    private static Map<String, Image> imageMap = new HashMap<>();
    private static Map<String, Class<? extends Sprite>> spritesClassMap = new HashMap<>();

    static {
        spritesClassMap.put("builder", Builder.class);
        spritesClassMap.put("engineer", Engineer.class);
        spritesClassMap.put("scout", Scout.class);
        spritesClassMap.put("truck", Truck.class);
        spritesClassMap.put("metal_mine", Metal.class);
        spritesClassMap.put("unobtainium_mine", Unobtainium.class);
        spritesClassMap.put("factory", Factory.class);
        spritesClassMap.put("pylon", Pylon.class);
        spritesClassMap.put("command_centre", CommandCentre.class);
    }

    public static List<List<Sprite>> readCSV(String path) {
        List<List<Sprite>> initList = new ArrayList<>();
        Scanner scanner = new Scanner(org.newdawn.slick.util.ResourceLoader.getResourceAsStream(path));
        while (scanner.hasNextLine()) {
            List<Sprite> spritesList = new ArrayList<>();
            String[] line;
            while (scanner.hasNextLine() && (line = scanner.nextLine().split(",")).length == 3) {
                String spriteName = line[0];
                int x = Integer.parseInt(line[1]);
                int y = Integer.parseInt(line[2]);
                try {
                    Sprite sprite = spritesClassMap.get(spriteName).newInstance();
                    sprite.setPos(new Vector2(x, y));
                    spritesList.add(sprite);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (!spritesList.isEmpty()) initList.add(spritesList);
        }
        scanner.close();
        return initList;
    }

    public static TiledMap readTiledMap(String path) {
        try {
            return new TiledMap(path);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Image readImage(String path) {
        if(imageMap.containsKey(path)) {
            return imageMap.get(path).copy();
        }
        else {
            try {
                Image image = new Image(path);
                imageMap.put(path, image);
                return image;
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String toSpriteClassName(String spriteName) {
        spriteName = spriteName.replace("_mine","");
        StringBuilder sb = new StringBuilder();
        int i = 0, j;
        while((j = spriteName.indexOf('_',i)) != -1) {
            sb.append(Character.toUpperCase(spriteName.charAt(i)));
            sb.append(spriteName, i+1, j);
            i = j+1;
        }
        sb.append(Character.toUpperCase(spriteName.charAt(i)));
        sb.append(spriteName, i+1, spriteName.length());
        return sb.toString();
    }
}
