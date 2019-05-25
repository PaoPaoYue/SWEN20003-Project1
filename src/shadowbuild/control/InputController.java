package shadowbuild.control;

import com.alibaba.fastjson.JSON;
import org.lwjgl.Sys;
import shadowbuild.helper.Logger;
import shadowbuild.util.Vector2;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class InputController {
    private static final int TIME_COUNT = 1000000;

    private Input input;
    private int timeStamp;

    private Queue<Input> inputQueue;

    public InputController() {
        input = Input.NO_INPUT;
        inputQueue = new LinkedList<>();
    }


    public void update(org.newdawn.slick.Input input, int delta) {
        if (canUpdate()) {
            boolean flag = false;
            byte[] keys = new byte[3];
            boolean isMouseLeftPressed = false;
            boolean isMouseRightPressed = false;
            double mousePosX = 0;
            double mousePosY = 0;
            if(input.isKeyPressed(org.newdawn.slick.Input.KEY_1)) {
                keys[0] = 1;
                flag = true;
            }
            if(input.isKeyPressed(org.newdawn.slick.Input.KEY_2)) {
                keys[1] = 1;
                flag = true;
            }
            if (input.isMousePressed(org.newdawn.slick.Input.MOUSE_LEFT_BUTTON)) {
                isMouseLeftPressed = true;
                flag = true;
            }
            if (input.isMousePressed(org.newdawn.slick.Input.MOUSE_RIGHT_BUTTON)) {
                isMouseRightPressed = true;
                flag = true;
            }
            if (flag) {
                Vector2 mouseInput = GameCoordinate.screenToWorld(new Vector2(input.getMouseX(), input.getMouseY()));
                mousePosX = mouseInput.getX();
                mousePosY = mouseInput.getY();
                this.input = new Input(timeStamp, keys, isMouseLeftPressed, isMouseRightPressed, mousePosX, mousePosY);
                this.inputQueue.add(this.input);
            } else {
                this.input = Input.NO_INPUT;
            }
        } else {
            this.input = Input.NO_INPUT;
        }
        timeStamp += timeStamp;
        timeStamp %= TIME_COUNT;
    }

    public Input getInput() {
        return this.input;
    }


    public List<Input> getInputQueue() {
        List<Input> res = (LinkedList)inputQueue;
        inputQueue = new LinkedList<>();
        return res;
    }

    private boolean canUpdate(){
        return !GameController.getGameUI().getTextbox().isAcceptingInput();
    }

}
