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
        StringBuilder result = new StringBuilder(src);
        int idxToCorrupt = RANDOM.nextInt(src.length());
        char charToCorrupt = result.charAt(idxToCorrupt);
        result.setCharAt(idxToCorrupt, charToCorrupt == '0' ? '1' : '0');
        return result.toString();
    }
}
