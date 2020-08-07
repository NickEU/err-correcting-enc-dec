package correcter;

public class CoderHamming {
    private static final int[] LEGIT_INDICES = {2, 4, 5, 6};
    private static final int[] PARITY_INDICES = {0, 1, 3};

    public static EncodedMsg encode(String msgBinary) {
        var expand = new StringBuilder();
        var parity = new StringBuilder();
        var bits = msgBinary.replaceAll(" ", "").toCharArray();
        for (int i = 0; i < bits.length; ) {
            var result = new StringBuilder("........");
            for (int idx : LEGIT_INDICES) {
                result.setCharAt(idx, bits[i++]);
            }
            expand.append(result).append(" ");
            // last bit is insignificant = always 0
            result.setCharAt(7, '0');
            for (int idx : PARITY_INDICES) {
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
        StringBuilder significantBits = new StringBuilder();
        var encodedBytes = receivedMsgBinary.split(" ");
        for (String encByte : encodedBytes) {
            int idOfBrokenBit = -1;
            for (int idx : PARITY_INDICES) {
                int onesInByte = countOnes(idx + 1, encByte);
                onesInByte = encByte.charAt(idx) == '1' ? onesInByte - 1 : onesInByte;
                char expectedParityBit = onesInByte % 2 == 0 ? '0' : '1';
                char actualParityBit = encByte.charAt(idx);
                if (expectedParityBit != actualParityBit) {
                    idOfBrokenBit += idx + 1;
                }
            }
            if (idOfBrokenBit != -1) {
                String properBit = encByte.charAt(idOfBrokenBit) == '0' ? "1" : "0";
                encByte = encByte.substring(0, idOfBrokenBit) + properBit + encByte.substring(idOfBrokenBit + 1);
            }
            for (int idx : LEGIT_INDICES) {
                significantBits.append(encByte.charAt(idx));
            }
        }

        int i = 8;
        while(i < significantBits.length()) {
            significantBits.insert(i++, " ");
            i += 8;
        }
        return significantBits.toString().stripTrailing();
    }
}
