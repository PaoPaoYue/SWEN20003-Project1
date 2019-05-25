package shadowbuild.network.message;

public class ConnectResponseMessage extends Message{
    private int id;

    public ConnectResponseMessage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CONNECT_RESPONSE;
    }
}
