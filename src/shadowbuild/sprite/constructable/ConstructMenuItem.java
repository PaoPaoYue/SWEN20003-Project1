package shadowbuild.sprite.constructable;

import shadowbuild.sprite.Sprite;

public class ConstructMenuItem {
    private Class<? extends Sprite> spriteClass;
    private int metalCost;
    private int unobtainiumCost;
    private int constructTime;

    public ConstructMenuItem(Class<? extends Sprite> spriteClass, int metalCost, int unobtainiumCost, int constructTime) {
        this.spriteClass = spriteClass;
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
