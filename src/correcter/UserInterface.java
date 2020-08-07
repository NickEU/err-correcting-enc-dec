package correcter;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Scanner;

public class UserInterface {
    private static final String SEND_FILENAME = "send.txt";
    private static final String ENCODED_FILENAME = "encoded.txt";
    private static final String RECEIVED_FILENAME = "received.txt";
    private static final String DECODED_FILENAME = "decoded.txt";

    void runStage5() {
        System.out.print("Write a mode: ");
        String userMode = new Scanner(System.in).nextLine().trim();
        switch(userMode) {
            case "encode":
                encodeMessage();
                break;
            case "decode":
                decodeMessage();
                break;
            case"send":
                sendMessage();
                break;
            default:
                System.out.println("Unrecognized command!");
        }
    }

    private void decodeMessage() {
        printHeader(RECEIVED_FILENAME);
        String receivedMsgBinary = Util.readFromFileInBinary(RECEIVED_FILENAME);
        printHexView(receivedMsgBinary);
        printBinView(receivedMsgBinary);
        printHeader(DECODED_FILENAME);
        System.out.println("correct: " + receivedMsgBinary);
        String decodedMsgClean = CoderHamming.decode(receivedMsgBinary);
        System.out.println("decode: " + decodedMsgClean);
        printHexView(decodedMsgClean);
        String originalMsg = Convert.binToText(decodedMsgClean);
        printTextView(originalMsg);
        Util.writeToFileInPlainText(DECODED_FILENAME, originalMsg);
    }

    private void sendMessage() {
        printHeader(ENCODED_FILENAME);
        String msgEncoded = Util.readFromFileInBinary(ENCODED_FILENAME);
        printHexView(msgEncoded);
        printBinView(msgEncoded);
        String msgWithErrors = ErrorGenerator.corruptBytes(msgEncoded);
        printHeader(RECEIVED_FILENAME);
        Util.writeToFileInBinary(RECEIVED_FILENAME, stringOfBytesToArrayOfByte(msgWithErrors));
        printBinView(msgWithErrors);
        printHexView(msgWithErrors);
    }

    private void encodeMessage() {
        printHeader(SEND_FILENAME);
        String msgOriginal = Util.readFromFileInPlainText(SEND_FILENAME);
        printTextView(msgOriginal);
        String msgBinary = Convert.textToBin(msgOriginal);
        printHexView(msgBinary);
        printBinView(msgBinary);
        printHeader(ENCODED_FILENAME);
        EncodedMsg encodedMsg = CoderHamming.encode(msgBinary);
        byte[] bytes = stringOfBytesToArrayOfByte(encodedMsg.getParity());
        Util.writeToFileInBinary(ENCODED_FILENAME, bytes);
        System.out.println(encodedMsg);
        printHexView(encodedMsg.getParity());
    }

    private byte[] stringOfBytesToArrayOfByte(String toBeConverted) {
        return Arrays.stream(toBeConverted.split(" "))
            .map(s -> (byte) Integer.parseInt(s, 2))
            .collect(ByteArrayOutputStream::new, ByteArrayOutputStream::write,
                (baos1, baos2) -> baos1.write(baos2.toByteArray(), 0, baos2.size()))
            .toByteArray();
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
