package app.rsatests;

import app.CryptoTest;
import app.UtilsForTest;
import org.junit.Test;

/**
 * This class tests the hash mechanisms for RSA
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public class RSAHashTest extends CryptoTest {

    /**
     *  This method tests hashing mechanism MD5 with
     *  the 1024 Bit keysize.
     */
    @Test
    public void rsa1024MD5Test() {
        config.addSetting("Type", "RSA");
        config.addSetting("HashType", "MD5");
        UtilsForTest.testRsaHashes(config, 1024, times);
    }

    /**
     *  This method tests hashing mechanism SHA-1 with
     *  the 1024 Bit keysize.
     */
    @Test
    public void rsa1024SHA1Test() {
        config.addSetting("Type", "RSA");
        config.addSetting("HashType", "SHA-1");
        UtilsForTest.testRsaHashes(config, 1024, times);
    }

    /**
     *  This method tests hashing mechanism SHA-224 with
     *  the 1024 Bit keysize.
     */
    @Test
    public void rsa1024SHA224Test() {
        config.addSetting("Type", "RSA");
        config.addSetting("HashType", "SHA-224");
        UtilsForTest.testRsaHashes(config, 1024, times);
    }

    /**
     *  This method tests hashing mechanism SHA-256 with
     *  the 1024 Bit keysize.
     */
    @Test
    public void rsa1024SHA256Test() {
        config.addSetting("Type", "RSA");
        config.addSetting("HashType", "SHA-256");
        UtilsForTest.testRsaHashes(config, 1024, times);
    }

    /**
     *  This method tests hashing mechanism SHA-384 with
     *  the 1024 Bit keysize.
     */
    @Test
    public void rsa1024SHA384Test() {
        config.addSetting("Type", "RSA");
        config.addSetting("HashType", "SHA-384");
        UtilsForTest.testRsaHashes(config, 1024, times);
    }

    /**
     *  This method tests hashing mechanism SHA-512 with
     *  the 1024 Bit keysize.
     */
    @Test
    public void rsa1024SHA512Test() {
        config.addSetting("Type", "RSA");
        config.addSetting("HashType", "SHA-512");
        UtilsForTest.testRsaHashes(config, 1024, times);
    }

    /**
     *  This method tests hashing mechanism MD5 with
     *  the 2048 Bit keysize.
     */
    @Test
    public void rsa2048MD5Test() {
        config.addSetting("Type", "RSA");
        config.addSetting("HashType", "MD5");
        UtilsForTest.testRsaHashes(config, 2048, times);
    }

    @Test
    public void rsa2048SHA1Test() {
        config.addSetting("Type", "RSA");
        config.addSetting("HashType", "SHA-1");
        UtilsForTest.testRsaHashes(config, 2048, times);
    }

    @Test
    public void rsa2048SHA224Test() {
        config.addSetting("Type", "RSA");
        config.addSetting("HashType", "SHA-224");
        UtilsForTest.testRsaHashes(config, 2048, times);
    }

    @Test
    public void rsa2048SHA256Test() {
        config.addSetting("Type", "RSA");
        config.addSetting("HashType", "SHA-256");
        UtilsForTest.testRsaHashes(config, 2048, times);
    }


    @Test
    public void rsa2048SHA384Test() {
        config.addSetting("Type", "RSA");
        config.addSetting("HashType", "SHA-384");
        UtilsForTest.testRsaHashes(config, 2048, times);
    }


    @Test
    public void rsa2048SHA512Test() {
        config.addSetting("Type", "RSA");
        config.addSetting("HashType", "SHA-512");
        UtilsForTest.testRsaHashes(config, 2048, times);
    }
}
