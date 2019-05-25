package shadowbuild.gui;

import org.newdawn.slick.Graphics;
import shadowbuild.control.GameController;
import shadowbuild.control.SpritesController;
import shadowbuild.player.PlayerGameInfo;
import shadowbuild.sprite.Selectable;
import shadowbuild.sprite.constructable.ConstructMenuItem;
import shadowbuild.sprite.constructable.Constructable;

public class GameUI extends GUI{

    private static final String PLAYER_INFO_PATTERN = "Metal: %d\nUnobtainium: %d";
    private static final String CREATE_OPTION = "%d- Create %s\n";

    public GameUI(UIstate state) {
        super(state);
    }

    @Override
    public void render(Graphics g) {
        PlayerGameInfo info = GameController.getMainPlayer().getGameInfo();
        g.drawString(String.format(PLAYER_INFO_PATTERN, info.getMetalAmount(), info.getUnobtainiumAmount()),
                32f, 32f);

        Selectable selectedSprite = SpritesController.getSelectedSprite();
        if (selectedSprite == null || !(selectedSprite instanceof Constructable)) return;
        else {
            Constructable constructable = (Constructable)selectedSprite;
            StringBuilder displayString = new StringBuilder();
            int i = 1;
            for (ConstructMenuItem item: constructable.getConstructMenu()) {
                displayString.append(String.format(CREATE_OPTION, i++, item.getName()));
            }
            g.drawString(displayString.toString(), 32f, 100f);
        }
    }
}
