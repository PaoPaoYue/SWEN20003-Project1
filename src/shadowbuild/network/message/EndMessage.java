package shadowbuild.network.message;

public class EndMessage extends Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.END;
    }
}
