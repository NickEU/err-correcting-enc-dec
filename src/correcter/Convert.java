package correcter;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Convert {
    static String[] toBinary(String msg) {
        byte[] bytes = msg.getBytes();
        return IntStream.range(0, bytes.length)
            .mapToObj(i -> Integer.toBinaryString(bytes[i]))
            .toArray(String[]::new);
    }

    static String fromBinary(String[] bytes) {
        return Arrays.stream(bytes)
            .map(b -> String.valueOf( (char) Integer.parseInt(b, 2)))
            .collect(Collectors.joining());
    }
}
