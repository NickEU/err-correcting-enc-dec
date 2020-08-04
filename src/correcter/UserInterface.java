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
                encodeMessage();
                break;
            case "decode":
                System.out.println("Decoding!");
                break;
            case"send":
                sendMessage();
                break;
            default:
                System.out.println("Unrecognized command!");
        }
    }

    private void sendMessage() {
        printHeader(ENCODED_FILENAME);
        String msgEncoded = Util.readFromFile(ENCODED_FILENAME);
        printHexView(msgEncoded);
        printBinView(msgEncoded);
        String msgWithErrors = ErrorGenerator.corruptBytes(msgEncoded);
        printHeader(RECEIVED_FILENAME);
        Util.writeToFile(RECEIVED_FILENAME, msgWithErrors);
        printBinView(msgWithErrors);
        printHexView(msgWithErrors);
    }

    private void encodeMessage() {
        printHeader(SEND_FILENAME);
        String msgOriginal = Util.readFromFile(SEND_FILENAME);
        printTextView(msgOriginal);
        String msgBinary = Convert.textToBin(msgOriginal);
        printHexView(msgBinary);
        printBinView(msgBinary);
        printHeader(ENCODED_FILENAME);
        EncodedMsg encodedMsg = Coder.encode(msgBinary);
        Util.writeToFile(ENCODED_FILENAME, encodedMsg.getParity());
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
