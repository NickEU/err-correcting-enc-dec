package correcter;

import java.util.Scanner;

import static correcter.ErrorGenerator.simulateErrors;

public class Main {
    public static void main(String[] args) {
        String msgToTransmit = new Scanner(System.in).nextLine();
        String encodedMsg = Util.encodeMsg(msgToTransmit);
        String corruptedMsg = simulateErrors(encodedMsg);
        String decodedMsg = Util.decodeMsg(corruptedMsg, 3);

        System.out.println(msgToTransmit + "\n" + encodedMsg + "\n" + corruptedMsg + "\n" + decodedMsg);
    }
}
