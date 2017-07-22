package app.AesTests;


import app.UtilsForTest;
import app.model.Configuration;


import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.*;

public class AesCbcTest {
    private static Configuration config;

    @BeforeClass
    public static void init() {
        config = new Configuration();
        config.addSetting("Type", "AES");
        config.addSetting("BlockModus", "CBC");
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
