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

        for (int i = 0; i < chars.length; i++) {
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
}
