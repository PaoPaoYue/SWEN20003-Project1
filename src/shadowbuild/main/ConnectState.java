package shadowbuild.main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import shadowbuild.control.StartPaneController;

public class ConnectState extends BasicGameState {

    private StartPaneController connectController;

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        connectController = StartPaneController.getInstance();
        connectController.init();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
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
