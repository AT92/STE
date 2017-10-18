package app.destests;


import app.CryptoTest;
import app.UtilsForTest;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class tests several configutaions for DES CTS encryption/decryption
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class DesCtsTest extends CryptoTest {

    /**
     * This method initializes the configuration.
     */
    @BeforeClass
    public static void init() {
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "CTS");
    }

    /**
     * This method tests DES CTS encryption/decryption for equality.
     */
    @Test
    public void testCts() {
        UtilsForTest.testEqual(config, times);
    }

    /**
     * This method tests DES Cts encryption/decryption and decrypts with a wrong
     * key.
     */
    @Test
    public void testCtsFalseKey() {
        UtilsForTest.testFalseKey(config, times);
    }
}
