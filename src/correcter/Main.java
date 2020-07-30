package correcter;

import java.util.Scanner;

import static correcter.ErrorGenerator.simulateErrors;
import static correcter.Util.decodeMsg;
import static correcter.Util.encodeMsg;

public class Main {
    public static void main(String[] args) {
        String msgToTransmit = new Scanner(System.in).nextLine();
        String encodedMsg = encodeMsg(msgToTransmit);
        String corruptedMsg = simulateErrors(encodedMsg);
        String decodedMsg = decodeMsg(corruptedMsg);

        System.out.println(msgToTransmit + "\n" + encodedMsg + "\n" + corruptedMsg + "\n" + decodedMsg);
    }
}
