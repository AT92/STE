package app.AES.ECB;


import app.UtilsForTest;
import app.logic.AES.ECB.AESECBDecryptor;
import app.logic.AES.ECB.AESECBEncryptor;
import app.logic.Decryptor;
import app.logic.Encryptor;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Random;

import static org.junit.Assert.*;

public class AESECBTest {


    @Test(expected = IllegalStateException.class)
    public void init() {
        try {
            Encryptor enc = new AESECBEncryptor(64, "NoPadding");
        } catch (NoSuchAlgorithmException|NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test256Bit() {
        for (int i = 0; i < 1000; i++) {
            String padding = UtilsForTest.getRandomPadding();
            String test = UtilsForTest.randomText(padding);
            String decContent = "";
            try {
                Encryptor encryptor256 = new AESECBEncryptor(256, padding);
                String key = encryptor256.getKeyAsString();
                String encContent = encryptor256.encryptAsString(test);
                Decryptor decryptor256 = new AESECBDecryptor(padding, key);
                decContent = decryptor256.decryptAsString(encContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertEquals(test, decContent);
        }
    }

    @Test
    public void test128Bit() {
        for (int i = 0; i < 1000; i++) {
            String padding = UtilsForTest.getRandomPadding();
            String test = UtilsForTest.randomText(padding);
            String decContent = "";
            try {
                Encryptor encryptor128 = new AESECBEncryptor(128, padding);
                String key = encryptor128.getKeyAsString();
                String encContent = encryptor128.encryptAsString(test);
                Decryptor decryptor128 = new AESECBDecryptor(padding, key);
                decContent = decryptor128.decryptAsString(encContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertEquals(test, decContent);
        }
    }

    @Test
    public void test192Bit() {
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            String padding = UtilsForTest.getRandomPadding();
            String test = UtilsForTest.randomText(padding);
            String decContent = "";
            try {
                Encryptor encryptor192 = new AESECBEncryptor(192, padding);
                String key = encryptor192.getKeyAsString();
                String encContent = encryptor192.encryptAsString(test);
                Decryptor decryptor192 = new AESECBDecryptor(padding, key);
                decContent = decryptor192.decryptAsString(encContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertEquals(test, decContent);
        }
    }
}
