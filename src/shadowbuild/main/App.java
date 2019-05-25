package shadowbuild.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;

public class App extends StateBasedGame {
    /** window width, in pixels */
    public static final int WINDOW_WIDTH = 1024;
    /** window height, in pixels */
    public static final int WINDOW_HEIGHT = 768;
    /** Developer mode */
    public static boolean DEBUG = true;

    public static StateBasedGame game;

    public static AppGameContainer container;

    public App() {
        super("Shadow Build");
        game = this;
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new ConnectState());
        addState(new PlayState());
    }

    public static void main(String[] args)
            throws SlickException {
        container = new AppGameContainer(new App());
        container.setTargetFrameRate(60);
        container.setShowFPS(true);
        container.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
        container.start();
    }

}
