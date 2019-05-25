package shadowbuild.network.message;

public class DeletePlayerMessage extends Message {
    private int id;

    public DeletePlayerMessage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.DELETE_PLAYER;
    }
}
