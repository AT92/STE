package app.destests;


import app.CryptoTest;
import app.UtilsForTest;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class tests several configutaions for DES CBC encryption/decryption
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class DesCbcTest extends CryptoTest {

    /**
     * This method initializes the configuration.
     */
    @BeforeClass
    public static void init() {
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "CBC");
    }

    /**
     * This method tests DES CBC encryption/decryption for equality.
     */
    @Test
    public void testCbc() {
        UtilsForTest.testEqual(config, times);
    }

    /**
     * This method tests DES CBC encryption/decryption and decrypts with a wrong
     * key.
     */
    @Test
    public void testCbcFalseKey() {
        UtilsForTest.testFalseKey(config, times);
    }
}
