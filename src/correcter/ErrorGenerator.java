package correcter;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ErrorGenerator {
    private static final Random RANDOM = new Random();

    static String simulateErrors(String text) {
        final int size = text.length();
        final int errorEveryXChars = 3;

        Set<Integer> indices = IntStream.iterate(0, i -> i + errorEveryXChars)
            .limit(size / errorEveryXChars)
            .map(i -> i + RANDOM.nextInt(errorEveryXChars))
            .boxed().collect(Collectors.toSet());

        return IntStream.range(0, text.length())
            .mapToObj(i -> {
                char ch = text.charAt(i);
                if (indices.contains(i)) {
                    ch = corruptChar(ch);
                }
                return String.valueOf(ch);
            }).collect(Collectors.joining());
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
