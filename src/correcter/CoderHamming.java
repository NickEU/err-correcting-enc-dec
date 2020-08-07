package correcter;

public class CoderHamming {
    public static EncodedMsg encode(String msgBinary) {
        int[] legitIndices = {2, 4, 5, 6};
        int[] parityIndices = {0, 1, 3};
        var expand = new StringBuilder();
        var parity = new StringBuilder();
        var bits = msgBinary.replaceAll(" ", "").toCharArray();
        for (int i = 0; i < bits.length; ) {
            var result = new StringBuilder("........");
            for (int idx : legitIndices) {
                result.setCharAt(idx, bits[i++]);
            }
            expand.append(result).append(" ");
            // last bit is insignificant = always 0
            result.setCharAt(7, '0');
            for (int idx : parityIndices) {
                int onesInByte = countOnes(idx + 1, result.toString());
                char parityBit = onesInByte % 2 == 0 ? '0' : '1';
                result.setCharAt(idx, parityBit);
            }
            parity.append(result).append(" ");
        }

        return new EncodedMsg(expand.toString().stripTrailing(), parity.toString().stripTrailing());
    }

    private static int countOnes(int pairs, String parity) {
        StringBuilder significantBits = new StringBuilder();
        int idx = pairs - 1;
        while (idx < parity.length()) {
            for (int i = 0; i < pairs; i++) {
                significantBits.append(parity.charAt(idx++));
            }
            idx += pairs;
        }

        return (int) significantBits.chars().filter(c -> c == '1').count();
    }

    public static String decode(String receivedMsgBinary) {
        return receivedMsgBinary;
    }
}
