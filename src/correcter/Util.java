package correcter;

import java.io.BufferedWriter;
import java.io.FileInputStream;
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
        try (FileOutputStream fs = new FileOutputStream(filename))
        {
            fs.write(bytes);
        } catch (IOException e) {
            System.out.println("Cannot write to file: " + e.getMessage());
        }
    }

    static String readFromFileInBinary(String filename) {
        try (FileInputStream fs = new FileInputStream(filename)) {
            StringBuilder result = new StringBuilder();
            int nextSignedByte = fs.read();
            while (nextSignedByte != -1) {
                //Byte.toUnsignedInt(unsignedByte);
                int nextUnsignedByte = 0xff & nextSignedByte;
                result.append(padByte(Integer.toBinaryString(nextUnsignedByte))).append(" ");
                nextSignedByte = fs.read();
            }
            return result.toString();
        } catch (IOException e) {
            System.out.println("Cannot read from file: " + e.getMessage());
            return "";
        }
    }

    static String padByte(String s) {
        var result = new StringBuilder(s);
        while (result.length() < 8) {
            result.insert(0, "0");
        }
        return result.toString();
    }
}
