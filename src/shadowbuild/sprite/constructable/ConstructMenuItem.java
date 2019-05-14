package shadowbuild.sprite.constructable;

import shadowbuild.sprite.Sprite;
import shadowbuild.sprite.SpritesParser;

public class ConstructMenuItem {
    private Class<? extends Sprite> spriteClass;
    private int metalCost;
    private int unobtainiumCost;
    private int constructTime;

    public ConstructMenuItem(String spriteClassName, int metalCost, int unobtainiumCost, int constructTime) {
        this.spriteClass = SpritesParser.getSpriteClass(spriteClassName);
        this.metalCost = metalCost;
        this.unobtainiumCost = unobtainiumCost;
        this.constructTime = constructTime;
    }

    Sprite getInstance() throws IllegalAccessException, InstantiationException {
        return spriteClass.newInstance();
    }

    public String getName() {
        return spriteClass.getSimpleName();
    }

    public int getMetalCost() {
        return metalCost;
    }

    public int getUnobtainiumCost() {
        return unobtainiumCost;
    }

    public int getConstructTime() {
        return constructTime;
    }
}
