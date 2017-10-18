package app.destests;

import app.CryptoTest;
import app.UtilsForTest;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class tests several configutaions for DES OFB encryption/decryption
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class DesOfbTest extends CryptoTest {

    /**
     * This method initializes the configuration.
     */
    @BeforeClass
    public static void init() {
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "OFB");
    }

    /**
     * This method tests DES OFB encryption/decryption for equality.
     */
    @Test
    public void testOfb() {
        UtilsForTest.testEqual(config, times);
    }

    /**
     * This method tests DES Ofb encryption/decryption and decrypts with a wrong
     * key.
     */
    @Test
    public void testOfbFalseKey() {
        UtilsForTest.testFalseKey(config, times);
    }
}
