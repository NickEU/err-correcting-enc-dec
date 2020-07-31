package correcter;

import java.util.Scanner;

public class UserInterface {
    private static final String SEND_FILENAME = "send.txt";
    private static final String ENCODED_FILENAME = "encoded.txt";
    private static final String RECEIVED_FILENAME = "received.txt";
    private static final String DECODED_FILENAME = "decoded.txt";

    void runStage4() {
        System.out.print("Write a mode: ");
        String userMode = new Scanner(System.in).nextLine().trim();
        switch(userMode) {
            case "encode":
                doEncodeStuff();
                break;
            case "decode":
                System.out.println("Decoding!");
                break;
            case"send":
                System.out.println("Sending!");
                break;
            default:
                System.out.println("Unrecognized command!");
        }
    }

    private void doEncodeStuff() {
        printHeader(SEND_FILENAME);
        String msgOriginal = Util.readFromFile(SEND_FILENAME);
        printTextView(msgOriginal);
        String msgBinary = Convert.textToBin(msgOriginal);
        printHexView(msgBinary);
        printBinView(msgBinary);
        printHeader(ENCODED_FILENAME);
        EncodedMsg encodedMsg = Coder.encode(msgBinary);
        System.out.println(encodedMsg);
        printHexView(encodedMsg.getParity());
    }

    private void printTextView(String msgText) {
        System.out.println("text view: " + msgText);
    }

    private void printBinView(String msgBinary) {
        System.out.println("bin view: " + msgBinary);
    }

    private void printHexView(String msgInBinary) {
        System.out.println("hex view: " + Convert.binToHex(msgInBinary));
    }

    private void printHeader(String fileName) {
        System.out.println("\n" + fileName + ":");
    }

}
