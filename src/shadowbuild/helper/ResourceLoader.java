package shadowbuild.helper;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import shadowbuild.sprite.Sprite;
import shadowbuild.sprite.SpritesParser;
import shadowbuild.util.Vector2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ResourceLoader {

    private static HashMap<String, Image> imageHashMap = new HashMap<>();

    public static List<List<Sprite>> readCSV(String path) {
        List<List<Sprite>> initList = new ArrayList<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            List<Sprite> spritesList = new ArrayList<>();
            String[] line;
            while (scanner.hasNextLine() && (line = scanner.nextLine().split(",")).length == 3) {
                String spriteName = line[0];
                int x = Integer.parseInt(line[1]);
                int y = Integer.parseInt(line[2]);
                try {
                    Sprite sprite = SpritesParser.getSpriteClass(toSpriteClassName(spriteName)).newInstance();
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
        if(imageHashMap.containsKey(path)) {
            return imageHashMap.get(path).copy();
        }
        else {
            try {
                Image image = new Image(path);
                imageHashMap.put(path, image);
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
