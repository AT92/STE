package app;


import app.model.Configuration;
import app.model.cryptomodes.des.ecb.DesEcbDecryptor;
import app.model.cryptomodes.des.ecb.DesEcbEncryptor;
import app.model.Decryptor;
import app.model.Encryptor;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DesEcbTest {



    private static Configuration config;

    @BeforeClass
    public static void init() {
        config = new Configuration();
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "ECB");
    }

    @Test
    public void testEcb() {
        Assert.assertTrue(UtilsForTest.testConfig(config, 500));
    }






    /*@Test
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
                String padding = UtilsForTest.getRandomPadding();
                String randomText = UtilsForTest.randomText(padding);
                try {
                    DesEcbEncryptor encryptor = new DesEcbEncryptor(padding);
                    encryptor.setKey(key);
                    String encryptedOnce = encryptor.encryptAsString(randomText);
                    String encryptedTwice = encryptor.encryptAsString(encryptedOnce);
                    assertEquals(randomText, encryptedTwice);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }*/
}
