package shadowbuild.control;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import shadowbuild.gui.ConnectUI;
import shadowbuild.gui.GUI;

public class StartPaneController {

    private GUI connectUI;

    /** Singleton pattern */
    private static StartPaneController instance;

    public static StartPaneController getInstance(){
        if (instance == null) {
            instance = new StartPaneController();
        }
        return instance;
    }

    private StartPaneController() {
        connectUI = new ConnectUI();
    }

    public void init(){
    }

    public void update(Input input, int delta){
    }

    public void render(Graphics g){
        connectUI.render(g);
    }
}
