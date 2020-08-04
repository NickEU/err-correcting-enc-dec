package correcter;

import java.util.Arrays;
import java.util.stream.Collectors;

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
            if (parityXor == -1) {
                parityXor = curChar;
            } else {
                parityXor ^= curChar;
            }
            idx++;
            if (idx % 3 == 0) {
                expand.append(" ");
                parity.append(parityXor).append(parityXor);
                parityXor = -1;
                parity.append(" ");
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
        return receivedMsg;
    }

    public static String finishDecoding(String decodedMsgRaw) {
        return decodedMsgRaw;
    }
}
