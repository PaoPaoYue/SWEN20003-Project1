package shadowbuild.control;

import shadowbuild.util.Vector2;

public class Input {

    public static final Input NO_INPUT = new Input(0, new byte[3], false, false, 0,0);

    public static final int KEY_1 = 0;
    public static final int KEY_2 = 1;
    public static final int KEY_3 = 2;

    private int timeStamp;
    private byte[] keys;
    private boolean MouseLeftPressed;
    private boolean MouseRightPressed;
    private double mousePosX;
    private double mousePosY;

    public Input(int timeStamp, byte[] keys, boolean MouseLeftPressed, boolean MouseRightPressed, double mousePosX, double mousePosY) {
        this.timeStamp = timeStamp;
        this.keys = keys;
        this.mousePosX = mousePosX;
        this.mousePosY = mousePosY;
        this.MouseLeftPressed = MouseLeftPressed;
        this.MouseRightPressed = MouseRightPressed;
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
        return MouseLeftPressed;
    }

    public boolean isMouseRightPressed() {
        return MouseRightPressed;
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
