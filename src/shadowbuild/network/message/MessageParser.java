package shadowbuild.network.message;

import com.alibaba.fastjson.JSON;
import shadowbuild.helper.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
public class MessageParser {

    public static <T extends Message> byte[] toBytes(T message) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            stream.write(message.getMessageType().getValue());
            stream.write(JSON.toJSONBytes(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.toByteArray();
    }

    public static <T extends Message> T toMessage(byte[] bytes) {
        MessageType type =  MessageType.fromByte(bytes[0]);
        Class<T> messageClass = getMessageClass(type);
        return messageClass.cast(JSON.parseObject(bytes, 1, bytes.length-1,(Charset)null, messageClass));
    }

    public static MessageType toMessageType(byte[] bytes) {
        return MessageType.fromByte(bytes[0]);
    }

    private static Class getMessageClass(MessageType type) {
        switch (type) {
            case CONNECT:
                return ConnectMessage.class;
            case CONNECT_RESPONSE:
                return ConnectResponseMessage.class;
            case ADD_PLAYER:
                return AddPlayerMessage.class;
            case DELETE_PLAYER:
                return DeletePlayerMessage.class;
            case TEXT:
                return TextMessage.class;
            case POST_GAME:
                return PostGameMessage.class;
            case START:
                return StartMessage.class;
            case PUBLISH_PLAYERS:
                return PublishPlayersMessage.class;
            case PUBLISH_GAME:
                return PublishGameMessage.class;
            case END:
                return EndMessage.class;
            default:
                Logger.error("MessageParse: Message type not found!!!");
                return null;
        }
    }
}
