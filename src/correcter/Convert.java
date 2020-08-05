package correcter;

import jdk.jshell.spi.ExecutionControl;

import java.util.Arrays;
import java.util.stream.Collectors;

class Convert {
    public static String textToBin(String msg) {
        return msg.chars()
            .mapToObj(Integer::toBinaryString)
            .map(s -> '0' + s + " ")
            .collect(Collectors.joining());
    }

    public static String binToHex(String msg) {
        return Arrays.stream(msg.split(" "))
            .mapToInt(b -> Integer.parseInt(b, 2))
            .mapToObj(Integer::toHexString)
            .map(String::toUpperCase)
            .map(s -> s.length() < 2 ? s.repeat(2) : s)
            .collect(Collectors.joining(" "));
    }

    public static String binToText(String decodedMsgClean) {
        return Arrays.stream(decodedMsgClean.split(" "))
            .map(b -> String.valueOf((char) Integer.parseInt(b, 2)))
            .collect(Collectors.joining(""));
    }
}
