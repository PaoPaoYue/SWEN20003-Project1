package shadowbuild.sprite.player;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import shadowbuild.control.GameController;
import shadowbuild.control.GameCoordinate;
import shadowbuild.sprite.*;
import shadowbuild.util.*;

public class Scout extends RectSprite{

    private static double SPEED = 0.25;

    public Scout(Image image){
        super(image);
    }

    @Override
    public void init() {
        setPos(new Vector2(GameCoordinate.WORLD_MIDDLE_X, GameCoordinate.WORLD_MIDDLE_Y));
    }

    @Override
    public void update(Input input, int delta) {
        if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            final Vector2 destiny = GameCoordinate.screenToWorld(new Vector2(input.getMouseX(), input.getMouseY()));
            if (input.isKeyDown(Input.KEY_LSHIFT)) {
                addTask(((sprite, task, delta1) -> {
                    if(!moveTowards(destiny, SPEED * delta1))
                        task.stop();
                }));
            } else {
                setTask(((sprite, task, coroutineDelta) -> {
                    if(!moveTowards(destiny, SPEED * coroutineDelta))
                        task.stop();
                }));
            }
        }
    }
}
