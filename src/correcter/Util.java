package correcter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Util {
    private final static String PROJECT_DIR = System.getProperty("user.dir");

    static String readFromFileInPlainText(String fileName) {
        try {
            return Files.lines(Paths.get(PROJECT_DIR, fileName))
                .collect(Collectors.joining());
        } catch (IOException e) {
            System.out.println("Cannot open/find file: " + e.getMessage());
            return "";
        }
    }

    static void writeToFileInPlainText(String fileName, String msg) {
        try (BufferedWriter writer = Files.newBufferedWriter(
            Paths.get(PROJECT_DIR, fileName), Charset.defaultCharset())) {
            writer.write(msg);
        } catch (IOException e) {
            System.out.println("Cannot write to file: " + e.getMessage());
        }
    }

    static void writeToFileInBinary(String filename, byte[] bytes) {
        try (FileOutputStream fStream = new FileOutputStream(filename))
        {
            fStream.write(bytes);
        } catch (IOException e) {
            System.out.println("Cannot write to file: " + e.getMessage());
        }
        System.out.println(Arrays.toString(bytes));
    }

    static byte[] readFromFileInBinary(String filename) {
        //Byte.toUnsignedInt(unsignedByte);
        return null;
    }
}
