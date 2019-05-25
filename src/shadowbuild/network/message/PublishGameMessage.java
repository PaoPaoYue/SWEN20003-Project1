package shadowbuild.network.message;

import shadowbuild.control.Input;

import java.util.List;

public class PublishGameMessage extends Message{

    int[] ids;
    private List<List<Input>> inputs;

    public PublishGameMessage(int ids[], List<List<Input>> inputs) {
        this.ids = ids;
        this.inputs = inputs;
    }

    public int[] getIds() {
        return ids;
    }

    public List<List<Input>> getInputs() {
        return inputs;
    }

    public List<Input> playerInputList(int id) {
        int i = 0;
        for(List<Input> inputList: inputs)
            if(ids[i++] == id)
                return inputList;
        return null;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.PUBLISH_GAME;
    }
}
