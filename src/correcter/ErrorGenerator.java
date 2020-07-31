package correcter;

import java.util.Random;

class ErrorGenerator {
    private static final Random RANDOM = new Random();

    static void simulateBitLevelErrors(byte[] chars) {
        for (int i = 0; i < chars.length; i++) {
            chars[i] = corruptByte(chars[i]);
        }
    }

    private static byte corruptByte(byte src) {
        return (byte) (src + 1);
    }
}
