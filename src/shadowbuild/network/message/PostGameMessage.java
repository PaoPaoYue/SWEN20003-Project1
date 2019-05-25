package shadowbuild.network.message;

import shadowbuild.control.Input;

import java.util.List;

public class PostGameMessage extends Message {
    private int id;
    private List<Input> inputList;

    public PostGameMessage(int id, List<Input> inputList) {
        this.id = id;
        this.inputList = inputList;
    }

    public int getId() {
        return id;
    }

    public List<Input> getInputList() {
        return inputList;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.POST_GAME;
    }
}
