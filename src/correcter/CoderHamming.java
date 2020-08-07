package correcter;

public class CoderHamming {
    public static EncodedMsg encode(String msgBinary) {
        return new EncodedMsg("00000000", "00000000");
    }

    public static String decode(String receivedMsgBinary) {
        return receivedMsgBinary;
    }
}
