package correcter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Collectors;

public class Util {
    private final static String PROJECT_DIR = System.getProperty("user.dir");

    static String readFromFile(String fileName) {
        try {
            return Files.lines(Paths.get(PROJECT_DIR, fileName))
                .collect(Collectors.joining());
        } catch (IOException e) {
            System.out.println("Cannot open/find file: " + e.getMessage());
            return "";
        }
    }

    static void writeToFile(String fileName, String receivedMsg) {
        try {
            Files.write(Paths.get(PROJECT_DIR, fileName),
                Collections.singleton(receivedMsg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
