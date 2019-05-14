package shadowbuild.sprite;

import shadowbuild.util.Vector2;

public interface Triggerable {

    static double TRIGGER_RANGE = 32;

    Vector2 getPos();

    boolean isTriggered();

    default boolean canTrigger(Vector2 pos) {
        return getPos().distance(pos) < TRIGGER_RANGE;
    };

    void trigger();
}
