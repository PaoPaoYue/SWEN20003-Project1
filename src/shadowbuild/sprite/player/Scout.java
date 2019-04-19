package shadowbuild.sprite.player;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import shadowbuild.control.GameCoordinate;
import shadowbuild.main.App;
import shadowbuild.sprite.*;
import shadowbuild.util.*;

public class Scout extends RectSprite{

    private static String IMG_PATH = "assets/scout.png";
    private static double SPEED = 0.25;

    public Scout() throws SlickException {
        super(new Image(IMG_PATH));
    }

    @Override
    public void init() {
        setPos(new Vector2(GameCoordinate.WORLD_MIDDLE_X, GameCoordinate.WORLD_MIDDLE_Y));
    }

    @Override
    public void update(Input input, int delta) {
        if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            /** Get the mouse input and convert to position in the world coordinate system */
            final Vector2 destination = GameCoordinate.screenToWorld(new Vector2(input.getMouseX(), input.getMouseY()));
            /** Set a coroutine movement task */
            setTask(((sprite, task, coroutineDelta) -> {
                if(!moveTowards(destination, SPEED * coroutineDelta))
                    task.stop();
            }));
        }
    }
}
