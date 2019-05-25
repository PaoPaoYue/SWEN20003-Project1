package shadowbuild.control;

import shadowbuild.util.Vector2;

public class Input {

    public static final Input NO_INPUT = new Input(0, new byte[3], false, false, 0,0);

    public static final int KEY_1 = 0;
    public static final int KEY_2 = 1;
    public static final int KEY_3 = 2;

    private int timeStamp;
    private byte[] keys;
    private boolean isMouseLeftPressed;
    private boolean isMouseRightPressed;
    private double mousePosX;
    private double mousePosY;

    public Input(int timeStamp, byte[] keys, boolean isMouseLeftPressed, boolean isMouseRightPressed, double mousePosX, double mousePosY) {
        this.timeStamp = timeStamp;
        this.keys = keys;
        this.mousePosX = mousePosX;
        this.mousePosY = mousePosY;
        this.isMouseLeftPressed = isMouseLeftPressed;
        this.isMouseRightPressed = isMouseRightPressed;
    }


    int getTimeStamp() {
        return timeStamp;
    }

    public byte[] getKeys() {
        return keys;
    }

    public boolean isKeyPressed(int i) {
        return keys[i] != 0;
    }

    public boolean isMouseLeftPressed() {
        return isMouseLeftPressed;
    }

    public boolean isMouseRightPressed() {
        return isMouseRightPressed;
    }

    public double getMousePosX() {
        return mousePosX;
    }

    public double getMousePosY() {
        return mousePosY;
    }

    public Vector2 mousePos() {
        return new Vector2(mousePosX, mousePosY);
    }
}
