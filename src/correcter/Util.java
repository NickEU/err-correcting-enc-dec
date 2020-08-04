package correcter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    static void writeToFile(String fileName, String msg) {
        try (BufferedWriter writer = Files.newBufferedWriter(
            Paths.get(PROJECT_DIR, fileName), Charset.defaultCharset())) {
            writer.write(msg);
        } catch (IOException e) {
            System.out.println("Cannot write to file: " + e.getMessage());
        }
    }
}
