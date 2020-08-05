package correcter;

import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Coder {
    public static EncodedMsg encode(String msgBinary) {
        StringBuilder expand = new StringBuilder();
        StringBuilder parity = new StringBuilder();
        int idx = 0;
        int parityXor = -1;
        for (char ch : msgBinary.replaceAll(" ", "").toCharArray()) {
            String doubleCh = ch + "" + ch;
            expand.append(doubleCh);
            parity.append(doubleCh);
            int curChar = ch == '0' ? 0 : 1;
            parityXor = parityXor == -1 ? curChar : parityXor ^ curChar;
            idx++;
            if (idx % 3 == 0) {
                expand.append(" ");
                parity.append(parityXor).append(parityXor).append(" ");
                parityXor = -1;
            }
        }
        String resultExpand = Arrays.stream(expand.toString().split(" "))
            .reduce("", (acc, cur) -> {
                cur = cur.concat(".".repeat(8)).substring(0, 8);
                return acc + " " + cur;
            }).stripLeading();
        String resultParity = Arrays.stream(parity.toString().split(" "))
            .map(s -> s.length() < 8 ? s.concat("0".repeat(8)).substring(0, 8) : s)
            .collect(Collectors.joining(" "));
        return new EncodedMsg(resultExpand, resultParity);
    }

    public static String decodeRaw(String receivedMsg) {
        var bits = Arrays.stream(receivedMsg.split(" "))
            .map(Coder::decodeByte)
            .collect(Collectors.joining());
        var result = new StringBuilder();
        for (int i = 0; i < bits.length(); i++) {
            if (i != 0 && i % 8 == 0) {
                result.append(" ");
            }
            result.append(bits.charAt(i));
        }
        return result.toString();
    }

    private static String decodeByte(String receivedByte) {
        Pattern pattern = Pattern.compile(".{2}");
        Matcher matcher = pattern.matcher(receivedByte);
        var bits = matcher.results()
            .map(MatchResult::group)
            .collect(Collectors.toList());

        boolean foundCorrupt = false;
        int corruptId = -1;
        for (int i = 0; i < bits.size() - 1; i++) {
            String bit = bits.get(i);
            if (bit.charAt(0) != bit.charAt(1)) {
                foundCorrupt = true;
                corruptId = i;
                break;
            }
        }
        if (foundCorrupt) {
            removeCorruptionFromBit(bits, corruptId);
        }

        return IntStream.range(0, 3)
            .mapToObj(i -> String.valueOf(bits.get(i).charAt(0)))
            .collect(Collectors.joining());
    }

    private static void removeCorruptionFromBit(List<String> bits, int corruptId) {
        int a = bits.get(0).charAt(0) == '0' ? 0 : 1;
        int b = bits.get(1).charAt(0) == '0' ? 0 : 1;
        int c = bits.get(2).charAt(0) == '0' ? 0 : 1;
        int parity = bits.get(3).charAt(0) == '0' ? 0 : 1;
        switch (corruptId) {
            case 0:
                bits.set(0, String.valueOf(b ^ c ^ parity).repeat(2));
                break;
            case 1:
                bits.set(1, String.valueOf(a ^ c ^ parity).repeat(2));
                break;
            case 2:
                bits.set(2, String.valueOf(a ^ b ^ parity).repeat(2));
                break;
        }
    }

    public static String finishDecoding(String decodedMsgRaw) {
        return Arrays.stream(decodedMsgRaw.split(" "))
            .filter(s -> s.length() == 8)
            .collect(Collectors.joining(" "));
    }
}
