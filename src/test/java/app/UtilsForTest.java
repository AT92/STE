package app;


import app.model.Configuration;
import app.model.Decryptor;
import app.model.Encryptor;
import app.model.cryptofactories.DecryptorFactory;
import app.model.cryptofactories.EncryptorFactory;
import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

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

    private static String getRandomPadding() {
        Random r = new Random();
        int option = r.nextInt() % 3;
        switch(option) {
            case 0: return "NoPadding";
            case 1: return "PKCS7Padding";
            case 2: return "ZeroBytePadding";
            default: return "NoPadding";
        }
    }

    private static String randomText(String padding) {
        String test = "";
        if (padding.equals("NoPadding")) {
            test = UtilsForTest.createRandomBlockedText();
        } else {
            test = UtilsForTest.createRandomNotBlockedText();
        }
        return test;
    }



    public static boolean testConfig(Configuration config, int times) {
        for (int i = 0; i < times; i++) {
            String padding = UtilsForTest.getRandomPadding();
            config.addSetting("Padding", padding);
            String randomText = UtilsForTest.randomText(padding);
            String decryptedText = encryptAndDecrypt(randomText, config);
            if (!randomText.equals(decryptedText)) {
                return false;
            }
        }
        return true;
    }

    private static String encryptAndDecrypt(String randomText, Configuration config) {
        try {
            Encryptor encryptor = EncryptorFactory.getEncryptor(config);
            String encContent = encryptor.encryptAsString(randomText);
            config = encryptor.updateConfiguration(config);
            Decryptor decryptor = DecryptorFactory.getDecryptor(config);
            return decryptor.decryptAsString(encContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
