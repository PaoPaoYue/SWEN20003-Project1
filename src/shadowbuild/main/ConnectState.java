package shadowbuild.main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import shadowbuild.control.GameController;
import shadowbuild.control.StartPaneController;

public class ConnectState extends BasicGameState {

    private StartPaneController connectController;

    private static boolean start = false;
    private static boolean isServer = false;

    public static void startPlay(boolean isServer) {
        start = true;
        ConnectState.isServer = isServer;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        connectController = StartPaneController.getInstance();
        connectController.init();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if(start) {
            if (isServer) {
                GameController.setServer();
            } else {
                GameController.setClient();
            }
            game.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
        Input input = container.getInput();
        connectController.update(input, delta);
    }


    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        connectController.render(g);
    }

    public int getID() {
        return 0;
    }

}
