package shadowbuild.sprite;

import shadowbuild.util.Vector2;

public interface Selectable {

    static double SELECT_RANGE = 32;

    Vector2 getPos();

    boolean isSelected();

    default boolean canSelect(Vector2 pos) {
        return getPos().distance(pos) < SELECT_RANGE;
    };

    void select();

    void unselect();
}
