package shadowbuild.network.message;

public class StartMessage extends Message {
    @Override
    public MessageType getMessageType() {
        return MessageType.START;
    }
}
