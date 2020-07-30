package correcter;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ErrorGenerator {
    private static final Random RANDOM = new Random();

    static String simulateErrors(String text) {
        final int size = text.length();

        Set<Integer> indices = IntStream.iterate(0, i -> i + 3)
            .limit(size / 3)
            .map(i -> i + RANDOM.nextInt(3))
            .boxed().collect(Collectors.toSet());

        return IntStream.range(0, text.length())
            .mapToObj(i -> String.valueOf(indices.contains(i)
                ? corruptChar(text.charAt(i))
                : text.charAt(i)))
            .collect(Collectors.joining());
    }

    static char corruptChar(char ch) {
        char potential;
        String legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";
        do {
            potential = legalChars.charAt(RANDOM.nextInt(legalChars.length()));
        } while (potential == ch);

        return potential;
    }
}
