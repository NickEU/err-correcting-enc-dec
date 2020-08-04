package correcter;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

class ErrorGenerator {
    private static final Map<Character, Character> zeroOne = Map.of(
        '0', '1',
        '1', '0');
    private static final Random RANDOM = new Random();

    public static String corruptBytes(String bytes) {
        return Arrays.stream(bytes.split(" "))
            .map(ErrorGenerator::corruptByte)
            .collect(Collectors.joining(" "));
    }

    private static String corruptByte(String original) {
        var result = new StringBuilder(original);
        int idxOfCorruptBit = RANDOM.nextInt(8);
        result.setCharAt(idxOfCorruptBit, zeroOne.get(original.charAt(idxOfCorruptBit)));
        return result.toString();
    }
}
