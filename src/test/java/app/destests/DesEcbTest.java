package app.destests;


import app.CryptoTest;
import app.UtilsForTest;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class tests several configutaions for DES ECB encryption/decryption
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class DesEcbTest extends CryptoTest {

    /**
     * This method initializes the configuration.
     */
    @BeforeClass
    public static void init() {
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "ECB");
    }

    /**
     * This method tests DES ECB encryption/decryption for equality.
     */
    @Test
    public void testEcb() {
        UtilsForTest.testEqual(config, times);
    }

    /**
     * This method tests DES ECB encryption/decryption and decrypts with a wrong
     * key.
     */
    @Test
    public void testEcbFalseKey() {
        UtilsForTest.testFalseKey(config, times);
    }
}
