package app.rsatests;

import app.CryptoTest;
import app.UtilsForTest;
import org.junit.Test;


/**
 * This class tests the RSA encryption/decryption
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class RSATest extends CryptoTest {

    /**
     * This method tests RSA encryption/decryption for equality
     * with the keysize of 1024 Bit.
     */
    @Test
    public void rsa1024Test() {
        config.addSetting("Type", "RSA");
        UtilsForTest.testRsa(config, 1024, times);
    }

    /**
     * This method tests RSA encryption/decryption for equality
     * with the keysize of 2048 Bit.
     */
    @Test
    public void rsa2048Test() {
        config.addSetting("Type", "RSA");
        UtilsForTest.testRsa(config, 2048, times);
    }

    /**
     * This method tests the RSA with a false private key,
     * with a size of 1024 Bit.
     */
    @Test
    public void rsa1024FalseKeyTest() {
        config.addSetting("Type", "RSA");
        config.addSetting("Keysize", "1024");
        UtilsForTest.testRSAFalseKey(config, times);
    }

    /**
     * This method tests the RSA with a false private key,
     * with a size of 2048 Bit.
     */
    @Test
    public void rsa2048FalseKeyTest() {
        config.addSetting("Type", "RSA");
        config.addSetting("Keysize", "2048");
        UtilsForTest.testRSAFalseKey(config, times);
    }
}
