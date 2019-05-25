package shadowbuild.network.message;

public class TextMessage extends Message {
    private int id;
    private String text;

    public TextMessage(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TEXT;
    }
}
