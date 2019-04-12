package shadowbuild.sprite.player;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import shadowbuild.control.GameController;
import shadowbuild.sprite.*;
import shadowbuild.util.*;

public class Scout extends RectSprite{

    private static double SPEED = 0.25;

    public Scout(Image image){
        super(image);
    }

    @Override
    public void init() {
        setPos(new Vector2(GameController.getInstance().mainTerrain.getWidth()/2,
                GameController.getInstance().mainTerrain.getHeight()/2));
    }

    @Override
    public void update(Input input, int delta) {
        if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            double x = input.getMouseX() + GameController.getInstance().mainCamera.getScope().getX();
            double y = input.getMouseY() + GameController.getInstance().mainCamera.getScope().getY();
            final Vector2 destiny = new Vector2(x, y);
            setTask(((sprite, task, delta1) -> {
                if(moveTowards(destiny, SPEED * delta1))
                    task.stop();
            }));
        }
    }
}
