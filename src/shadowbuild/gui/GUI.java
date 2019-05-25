package shadowbuild.gui;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import shadowbuild.main.App;

public abstract class GUI {

    public final static Font font = App.container.getDefaultFont();
    public final static int CHAR_WIDTH = font.getWidth("s");
    public final static int LINE_HEIGHT = font.getLineHeight();

    private UIstate defaultState;
    private UIstate currentState;

    public GUI(UIstate state){
        defaultState = state;
        currentState = state;
    }

    public abstract void render(Graphics g);

    public void enterState(UIstate state){
        currentState = state;
    }

    public void exitState() {
        currentState = defaultState;
    }

    public UIstate getCurrentState() {
        return currentState;
    }

    public UIstate getDefaultState() {
        return defaultState;
    }

}
