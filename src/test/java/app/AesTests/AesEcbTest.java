package app.AesTests;


import app.UtilsForTest;
import app.model.Configuration;
import app.model.cryptomodes.aes.ecb.AesEcbDecryptor;
import app.model.cryptomodes.aes.ecb.AesEcbEncryptor;
import app.model.Decryptor;
import app.model.Encryptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class AesEcbTest {

    private static Configuration config;

    @BeforeClass
    public static void init() {
        config = new Configuration();
        config.addSetting("Type", "AES");
        config.addSetting("BlockModus", "ECB");
    }

    @Test
    public void test256Bit() {
        config.addSetting("Keysize", "256");
        Assert.assertTrue(UtilsForTest.testConfig(config, 500));
    }

    @Test
    public void test192Bit() {
        config.addSetting("Keysize", "192");
        Assert.assertTrue(UtilsForTest.testConfig(config, 500));
    }

    @Test
    public void test128Bit() {
        config.addSetting("Keysize", "128");
        Assert.assertTrue(UtilsForTest.testConfig(config, 500));
    }
}
