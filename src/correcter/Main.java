package correcter;

public class Main {
    public static void main(String[] args) {
        runStage3();
    }

    private static void runStage3() {
        String originalMsg = Util.readFromFile("send.txt");
        String[] bytes = Convert.toBinary(originalMsg);
        ErrorGenerator.simulateBitLevelErrors(bytes);
        String receivedMsg = Convert.fromBinary(bytes);
        Util.writeToFile("received.txt", receivedMsg);
    }
}
