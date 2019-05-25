package shadowbuild.gui.componet;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import shadowbuild.main.App;

public class Button {
    private static Color mouseOverColor = new Color(255,255,255,120);
    private static Color mouseDownColor = new Color(30,30,30,30);

    private Image image;
    private MouseOverArea mouseOverArea;

    public Button(int x, int y, Image image) {
        this.image = image;
        this.mouseOverArea = new MouseOverArea(App.container, image, x, y, image.getWidth(), image.getHeight());
        mouseOverArea.setMouseDownColor(mouseDownColor);
        mouseOverArea.setMouseOverColor(mouseOverColor);
    }

    public void addListener(ComponentListener listener) {
        mouseOverArea.addListener(listener);
    }

    public void render(Graphics g) {
        mouseOverArea.render(App.container, g);
    }
}
