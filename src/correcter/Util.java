package correcter;

public class Util {
    static String encodeMsg(String msgToTransmit) {
        return msgToTransmit.replaceAll("(.)", "$1$1$1");
    }

    static String decodeMsg(String corruptedMsg) {
        int repeatsPerChar = 3;

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < corruptedMsg.length(); i += repeatsPerChar) {
            String sub = corruptedMsg.substring(i, i + repeatsPerChar);
            char legit = sub.charAt(0) == sub.charAt(1)
                ? sub.charAt(0)
                : sub.charAt(2);
            result.append(legit);
        }
        return result.toString();
    }
}
