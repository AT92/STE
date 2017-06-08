package app;


import app.logic.AES.AESDecrypto;
import app.logic.AES.AESEncrypto;
import org.junit.Before;
import org.junit.Test;

import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Random;

import static org.junit.Assert.*;

public class AESTest {
    private AESEncrypto encryptor128;
    private AESEncrypto encryptor192;
    private AESEncrypto encryptor256;
    private AESDecrypto decryptor128;
    private AESDecrypto decryptor192;
    private AESDecrypto decryptor256;

    @Test(expected = IllegalStateException.class)
    public void init() {
        try {
            AESEncrypto enc = new AESEncrypto(64);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException n) {
            n.printStackTrace();
        }
    }

    @Test
    public void test256Bit() {
        RandomStringUtils r = new RandomStringUtils();
        Random r2 = new Random();
        for (int i = 0; i < 1000; i++) {
            int textSize = Math.abs(r2.nextInt(32768) << 4);
            String test = r.randomAscii(textSize);
            String decContent = "";
            try {
                encryptor256 = new AESEncrypto(256);
                String key = encryptor256.getKey();
                String encContent = encryptor256.encryptAES(test);
                decryptor256 = new AESDecrypto(key);
                decContent = decryptor256.decryptAES(encContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertEquals(test, decContent);
        }
    }

    @Test
    public void test128Bit() {
        RandomStringUtils r = new RandomStringUtils();
        Random r2 = new Random();
        for (int i = 0; i < 1000; i++) {
            int textSize = Math.abs(r2.nextInt(32768) << 4);
            String test = r.randomAscii(textSize);
            String decContent = "";
            try {
                encryptor128 = new AESEncrypto(128);
                String key = encryptor128.getKey();
                String encContent = encryptor128.encryptAES(test);
                decryptor128 = new AESDecrypto(key);
                decContent = decryptor128.decryptAES(encContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertEquals(test, decContent);
        }
    }

    @Test
    public void test192Bit() {
        RandomStringUtils r = new RandomStringUtils();
        Random r2 = new Random();
        for (int i = 0; i < 1000; i++) {
            int textSize = Math.abs(r2.nextInt(32768) << 4);
            String test = r.randomAscii(textSize);
            String decContent = "";
            try {
                encryptor192 = new AESEncrypto(192);
                String key = encryptor192.getKey();
                String encContent = encryptor192.encryptAES(test);
                decryptor192 = new AESDecrypto(key);
                decContent = decryptor192.decryptAES(encContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertEquals(test, decContent);
        }
    }
}
