package shadowbuild.main;

/**
 * Sample Project for SWEN20003: Object Oriented Software Development, 2019, Semester 1
 * by Eleanor McMurtry, University of Melbourne
 */

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

import shadowbuild.control.GameController;

/**
 * Main class for the game.
 * Handles initialisation, input and rendering.
 */
public class App extends BasicGame {
    /** window width, in pixels */
    public static final int WINDOW_WIDTH = 1024;
    /** window height, in pixels */
    public static final int WINDOW_HEIGHT = 768;
    /** Developer mode */
    public static boolean DEBUG = false;

    public GameController game;

    public App() {
        super("Shadow Build");
    }

    @Override
    public void init(GameContainer gc)
            throws SlickException {
        game = new GameController();
        game.init(gc);
    }

    /** Update the game state for a frame.
     * @param gc The Slick game container object.
     * @param delta Time passed since last frame (milliseconds).
     */
    @Override
    public void update(GameContainer gc, int delta)
            throws SlickException {
        // Get data about the current input (keyboard state).
        Input input = gc.getInput();
        game.update(input, delta);
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param gc The Slick game container object.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(GameContainer gc, Graphics g)
            throws SlickException {
        game.render(g);
    }

    /** Start-up method. Creates the game and runs it.
     * @param args Command-line arguments (ignored).
     */
    public static void main(String[] args)
            throws SlickException {
        AppGameContainer app = new AppGameContainer(new App());
        app.setShowFPS(true);
        app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
        app.start();
    }

}
