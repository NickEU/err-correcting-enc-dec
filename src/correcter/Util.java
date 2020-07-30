package correcter;

public class Util {
    public static String encodeMsg(String msgToTransmit) {
        return msgToTransmit.replaceAll("(.)", "$1$1$1");
    }

    public static String decodeMsg(String corruptedMsg, int repeatsPerChar) {
        if (repeatsPerChar < 3) {
            throw new IllegalStateException();
        }
        // assumes 1 mistake per sequence

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < corruptedMsg.length(); i += repeatsPerChar) {
            String sub = corruptedMsg.substring(i, i + repeatsPerChar);
            char legit = sub.indexOf(sub.charAt(0)) == sub.lastIndexOf(sub.charAt(0))
                ? sub.charAt(1)
                : sub.charAt(0);
            result.append(legit);
        }
        return result.toString();
    }
}
