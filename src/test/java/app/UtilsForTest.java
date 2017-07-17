package app;


import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class UtilsForTest {
    private static String createRandomBlockedText() {
        Random r = new Random();
        int textSize = Math.abs(r.nextInt(32768) << 4);
        return RandomStringUtils.randomAscii(textSize);
    }

    private static String createRandomNotBlockedText() {
        Random r = new Random();
        int textSize = Math.abs(r.nextInt(32768));
        return RandomStringUtils.randomAscii(textSize);
    }

    public static String getRandomPadding() {
        Random r = new Random();
        int option = r.nextInt() % 3;
        switch(option) {
            case 0: return "NoPadding";
            case 1: return "PKCS7Padding";
            case 2: return "ZeroBytePadding";
            default: return "NoPadding";
        }
    }

    public static String randomText(String padding) {
        String test = "";
        if (padding.equals("NoPadding")) {
            test = UtilsForTest.createRandomBlockedText();
        } else {
            test = UtilsForTest.createRandomNotBlockedText();
        }
        return test;
    }
}
