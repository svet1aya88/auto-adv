package core.utilities.random;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class Randomizer {

    private static final int CHARS_COUNT_BEFORE_SPACE = 7;
    private static final String SPACE = " ";

    public static String generateAlphanumericString(int minLength, int maxLength) {
        return RandomStringUtils.randomAlphanumeric(minLength, maxLength);
    }

    public static String generateAlphanumericStringWithSpaces() {
        return generateAlphanumericStringWithSpaces(5, 20);
    }

    public static String generateAlphanumericStringWithSpaces(int minLength, int maxLength) {
        String randomString = "";
        char[] chars = generateAlphanumericString(minLength, maxLength).toCharArray();

        for (int i = 0; i < maxLength; i++) {
            if (i % CHARS_COUNT_BEFORE_SPACE == 0 && i > 0) {
                randomString = randomString.concat(SPACE);
            } else {
                randomString = randomString.concat(String.valueOf(chars[i]));
            }
        }
        return randomString;
    }

    public static String generateStringWithSpecialSymbols(int minLength, int maxLength) {
        String specialSymbols = "!@#$%^&*()_+-=`~[]{};':\"|,./<>?";
        int symbolsLength = specialSymbols.length();
        if (symbolsLength < maxLength) {
            int additionalSymbolsLength = maxLength - symbolsLength;
            specialSymbols = specialSymbols.concat(RandomStringUtils.randomAlphanumeric(additionalSymbolsLength));
        } else {
            int reducedSymbolLength = symbolsLength - maxLength;
            specialSymbols = removeRandomChars(specialSymbols, reducedSymbolLength, minLength);
        }
        return specialSymbols;
    }

    private static String removeRandomChars(String input, int charsToRemove, int minLength) {
        if (minLength >= input.length()) {
            return input;
        }
        if (charsToRemove <= 0) {
            return input.substring(0, minLength);
        }

        if (charsToRemove >= input.length() - minLength) {
            return input.substring(0, minLength);
        }

        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(input);

        while (stringBuilder.length() > minLength && charsToRemove > 0) {
            int randomIndex = random.nextInt(stringBuilder.length());
            stringBuilder.deleteCharAt(randomIndex);
            charsToRemove--;
        }
        return stringBuilder.toString();
    }

    public static String generateMultiLanguageString(int minLength, int maxLength) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        int length = minLength + random.nextInt(maxLength - minLength + 1);

        for (int i = 0; i < length; i++) {
            char randomChar = generateRandomChar();
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    private static char generateRandomChar() {
        Random random = new Random();
        int category = random.nextInt(4);
        char spaceChar = ' ';

        return switch (category) {
            case 0 ->
                // Cyrillic characters
                    (char) (0x0410 + random.nextInt(32)); // Range: 0x0410 to 0x043F
            case 1 ->
                // Umlaut characters (e.g., German)
                    (char) (0x00C0 + random.nextInt(6)); // Range: 0x00C0 to 0x00C5
            case 2 ->
                // Chinese characters (simplified)
                    (char) (0x4E00 + random.nextInt(20902)); // Range: 0x4E00 to 0x9FFF
            case 3 ->
                // Space character
                    spaceChar;
            default -> spaceChar;
        };
    }
}
