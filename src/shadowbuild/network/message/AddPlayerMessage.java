package shadowbuild.network.message;

import shadowbuild.player.Player;

import java.util.ArrayList;
import java.util.List;

public class AddPlayerMessage extends Message{
    private int[] ids;
    private String[] names;

    public AddPlayerMessage(int[] ids, String[] names) {
        this.ids = ids;
        this.names = names;
    }

    public int[] getIds() {
        return ids;
    }

    public String[] getNames() {
        return names;
    }

    public List<Player> otherPlayers() {
        List<Player> res = new ArrayList<>();
        for (int i = 0; i < getIds().length; i++) {
            res.add(new Player(getIds()[i],getNames()[i]));
        }
        return  res;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.ADD_PLAYER;
    }
}
