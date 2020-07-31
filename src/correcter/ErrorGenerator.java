package correcter;

import java.util.Random;

class ErrorGenerator {
    private static final Random RANDOM = new Random();

    static void simulateBitLevelErrors(String[] chars) {
        for (int i = 0; i < chars.length; i++) {
            chars[i] = corruptByte(chars[i]);
        }
    }

    private static String corruptByte(String src) {
        return src;
    }
}
