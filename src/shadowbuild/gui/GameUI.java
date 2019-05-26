package shadowbuild.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import shadowbuild.control.GameController;
import shadowbuild.control.SpritesController;
import shadowbuild.gui.componet.Textbox;
import shadowbuild.player.Player;
import shadowbuild.player.PlayerGameInfo;
import shadowbuild.sprite.Selectable;
import shadowbuild.sprite.constructable.ConstructMenuItem;
import shadowbuild.sprite.constructable.Constructable;

import java.util.Collections;
import java.util.List;

public class GameUI extends GUI{

    private static final String PLAYER_INFO_PATTERN = "Metal: %d\nUnobtainium: %d";
    private static final String CREATE_OPTION = "%d- Create %s\n";
    private static final String PLAYERS_PATTERN = "[%d] %-8s Metal: %4d Unobtainium: %4d";

    private Textbox textbox;

    public GameUI(UIstate state) {
        super(state);
        textbox = new Textbox(50, 800, 8, 40);
    }

    public Textbox getTextbox() {
        return textbox;
    }

    @Override
    public void render(Graphics g) {
        textbox.render(g);
        g.setColor(Color.white);

        g.drawString("                   Rankings                   \n______________________________________________",
                600f, 12f);
        float y = 56f;
        int i = 1;
        List<Player> allPlayers = GameController.getAllPlayers();
        Collections.sort(allPlayers, ((o1, o2) -> o2.getGameInfo().getUnobtainiumAmount() - o1.getGameInfo().getUnobtainiumAmount()));
        for (Player player: allPlayers) {
            g.drawString(String.format(PLAYERS_PATTERN, i, player.getPlayerName(), player.getGameInfo().getMetalAmount(), player.getGameInfo().getUnobtainiumAmount()),
                    620f, y);
            i++;
            y += GUI.LINE_HEIGHT + 10;
        }


        PlayerGameInfo info = GameController.getMainPlayer().getGameInfo();
        g.drawString(String.format(PLAYER_INFO_PATTERN, info.getMetalAmount(), info.getUnobtainiumAmount()),
                32f, 32f);

        Selectable selectedSprite = SpritesController.getSelectedSprite();
        if (selectedSprite == null || !(selectedSprite instanceof Constructable)) return;
        else {
            Constructable constructable = (Constructable)selectedSprite;
            StringBuilder displayString = new StringBuilder();
            int j = 1;
            for (ConstructMenuItem item: constructable.getConstructMenu()) {
                displayString.append(String.format(CREATE_OPTION, j++, item.getName()));
            }
            g.drawString(displayString.toString(), 32f, 100f);
        }

    }
}
