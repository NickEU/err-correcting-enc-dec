package correcter;

import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class CoderBad {
    public static EncodedMsg encode(String msgBinary) {
        StringBuilder expand = new StringBuilder();
        StringBuilder parity = new StringBuilder();
        int idx = 0;
        int parityXor = -1;
        for (char ch : msgBinary.replaceAll(" ", "").toCharArray()) {
            String doubleCh = ch + "" + ch;
            expand.append(doubleCh);
            parity.append(doubleCh);
            int curChar = Character.getNumericValue(ch);
            parityXor = parityXor == -1 ? curChar : parityXor ^ curChar;
            idx++;
            if (idx % 3 == 0) {
                expand.append(" ");
                parity.append(parityXor).append(parityXor).append(" ");
                parityXor = -1;
            }
        }
        // fix last byte if the num of significant bits isn't divisible by 3
        var encodedBytes = parity.toString().split(" ");
        StringBuilder lastByte = new StringBuilder(encodedBytes[encodedBytes.length - 1]);
        if (lastByte.length() < 8) {
            lastByte.append("00");
            parityXor ^= 0;
            while (lastByte.length() < 8) {
                lastByte.append(parityXor).append(parityXor);
                parityXor ^= Character.getNumericValue(lastByte.charAt(lastByte.length() - 3));
            }
        }

        encodedBytes[encodedBytes.length - 1] = lastByte.toString();

        String resultExpand = Arrays.stream(expand.toString().split(" "))
            .reduce("", (acc, cur) -> {
                cur = cur.concat(".".repeat(8)).substring(0, 8);
                return acc + " " + cur;
            }).stripLeading();
        return new EncodedMsg(resultExpand, String.join(" ", encodedBytes));
    }

    public static String decodeRaw(String receivedMsg) {
        var bits = Arrays.stream(receivedMsg.split(" "))
            .map(CoderBad::decodeByte)
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
        int a = Character.getNumericValue(bits.get(0).charAt(0));
        int b = Character.getNumericValue(bits.get(1).charAt(0));
        int c = Character.getNumericValue(bits.get(2).charAt(0));
        int parity = Character.getNumericValue(bits.get(3).charAt(0));
        int first;
        int second;
        switch (corruptId) {
            case 0:
                first = b;
                second = c;
                break;
            case 1:
                first = a;
                second = c;
                break;
            case 2:
                first = a;
                second = b;
                break;
            default:
                throw new IllegalStateException();
        }
        bits.set(corruptId, String.valueOf(first ^ second ^ parity).repeat(2));
    }

    public static String finishDecoding(String decodedMsgRaw) {
        var bytes = decodedMsgRaw.split(" ");
        String lastByte = bytes[bytes.length - 1];
        if (lastByte.length() < 8 && lastByte.contains("1")) {
            bytes[bytes.length - 2] = bytes[bytes.length - 2].substring(0, 7) + "1";
        }

        return Arrays.stream(bytes)
            .filter(s -> s.length() == 8)
            .collect(Collectors.joining(" "));
    }
}
