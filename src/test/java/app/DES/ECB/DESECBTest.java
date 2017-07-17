package app.DES.ECB;


import app.UtilsForTest;
import app.logic.DES.ECB.DESECBDecryptor;
import app.logic.DES.ECB.DESECBEncryptor;
import app.logic.Decryptor;
import app.logic.Encryptor;
import org.junit.Test;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DESECBTest {

    @Test
    public void testDES() {
        for (int i = 0; i < 1000; i++) {
            String padding = UtilsForTest.getRandomPadding();
            String test = UtilsForTest.randomText(padding);
            String decContent = "";
            try {
                Encryptor encryptor = new DESECBEncryptor(padding);
                String key = encryptor.getKeyAsString();
                String encContent = encryptor.encryptAsString(test);
                Decryptor decryptor = new DESECBDecryptor(padding, key);
                decContent = decryptor.decryptAsString(encContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertEquals(test, decContent);
        }
    }

    @Test
    public void testWeakKeys() {
        LinkedList<byte[]> weakKeys = new LinkedList<>();
        byte[] weakKey1 = {0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01};
        byte[] weakKey2 = {0x1F, 0x1F, 0x1F, 0x1F, 0x0E, 0x0E, 0x0E, 0x0E};
        byte[] weakKey3 = {(byte)0xFE, (byte)0xFE, (byte)0xFE, (byte)0xFE, (byte)0xFE, (byte)0xFE, (byte)0xFE, (byte)0xFE};
        byte[] weakKey4 = {(byte)0xE0, (byte)0xE0, (byte)0xE0, (byte)0xE0, (byte)0xF1, (byte)0xF1, (byte)0xF1, (byte)0xF1};
        weakKeys.add(weakKey1);
        weakKeys.add(weakKey2);
        weakKeys.add(weakKey3);
        weakKeys.add(weakKey4);
        for (byte[] bytes: weakKeys) {
            SecretKeySpec key = new SecretKeySpec(bytes, "DES");
            for (int i = 0; i < 1000; i++) {
                String test = UtilsForTest.randomText("NoPadding");
                try {
                    DESECBEncryptor encrypto = new DESECBEncryptor("NoPadding");
                    encrypto.setKey(key);
                    byte[] encContent = encrypto.encrypt(test.getBytes(StandardCharsets.US_ASCII));
                    byte[] encContent2 = encrypto.encrypt((encContent));
                    assertTrue(Arrays.equals(test.getBytes(StandardCharsets.US_ASCII), encContent2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
