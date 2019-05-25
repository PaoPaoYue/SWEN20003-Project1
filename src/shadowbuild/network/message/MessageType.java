package shadowbuild.network.message;

public enum MessageType {
    CONNECT((byte)0),
    CONNECT_RESPONSE((byte)1),
    ADD_PLAYER((byte)2),
    DELETE_PLAYER((byte)3),
    TEXT((byte)4),
    POST_GAME((byte)5),
    START((byte)6),
    PUBLISH_PLAYERS((byte)7),
    PUBLISH_GAME((byte)8),
    END((byte)9);


    private final byte value;
    private static final MessageType[] types = values();

    private MessageType(byte value) {
        this.value = value;
    }

    public byte[] getValue() {
        return new byte[]{this.value};
    }

    public static MessageType fromByte(byte type) {
        try {
            return types[type];
        } catch (ArrayIndexOutOfBoundsException var2) {
            throw new IllegalArgumentException("Type byte " + type + " not found");
        }
    }
}
