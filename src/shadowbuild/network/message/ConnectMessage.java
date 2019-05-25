package shadowbuild.network.message;

public class ConnectMessage extends Message {

    private String username;

    public ConnectMessage(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CONNECT;
    }
}
