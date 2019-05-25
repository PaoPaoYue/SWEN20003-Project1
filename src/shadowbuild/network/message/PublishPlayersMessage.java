package shadowbuild.network.message;

import shadowbuild.helper.Logger;

import java.util.Arrays;

public class PublishPlayersMessage extends Message {
    private int[] ids;
    private int[] delays;

    public PublishPlayersMessage(int[] ids, int[] delays) {
        this.ids = ids;
        this.delays = delays;
    }

    public int[] getIds() {
        return ids;
    }

    public int[] getDelays() {
        return delays;
    }

    public int playerDelay(int id) {
        int i = 0;
        for (int _id : ids) {
            if(_id==id) break;
            i++;
        }
        return i>=ids.length? 0: delays[i];
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.PUBLISH_PLAYERS;
    }
}
