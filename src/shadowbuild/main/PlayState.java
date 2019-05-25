package shadowbuild.main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import shadowbuild.control.GameController;

public class PlayState extends BasicGameState {

    private GameController gameController;

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        gameController = GameController.getInstance();
        gameController.init();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        gameController.update(input, delta);
    }


    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        gameController.render(g);
    }

    public int getID() {
        return 1;
    }

}
