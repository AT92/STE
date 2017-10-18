package app.hashtest;


import app.CryptoTest;
import app.UtilsForTest;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class test the hashing mechanism for several
 * ciphers, besides RSA.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class HashTest extends CryptoTest {

    @BeforeClass
    public static void setKeysize() {
        config.addSetting("Keysize", "192");
    }

    /**
     * This method tests hashing with AES OFB and MD5.
     */
    @Test
    public void md5AesOfbTest() {
        config.addSetting("HashType", "MD5");
        config.addSetting("Type", "AES");
        config.addSetting("BlockModus", "OFB");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with AES CBC and MD5.
     */
    @Test
    public void md5AesCbcTest() {
        config.addSetting("HashType", "MD5");
        config.addSetting("Type", "AES");
        config.addSetting("BlockModus", "CBC");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with DES OFB and MD5.
     */
    @Test
    public void md5DesOfbTest() {
        config.addSetting("HashType", "MD5");
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "OFB");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with DES CBC and MD5.
     */
    @Test
    public void md5DesCbcTest() {
        config.addSetting("HashType", "MD5");
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "CBC");
        UtilsForTest.testHash(config, times);
    }

    /* SHA-1*/

    /**
     * This method tests hashing with AES OFB and SHA-1.
     */
    @Test
    public void sha1AesOfbTest() {
        config.addSetting("HashType", "SHA-1");
        config.addSetting("Type", "AES");
        config.addSetting("BlockModus", "OFB");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with AES CBC and SHA-1.
     */
    @Test
    public void sha1AesCbcTest() {
        config.addSetting("HashType", "SHA-1");
        config.addSetting("Type", "AES");
        config.addSetting("BlockModus", "CBC");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with DES OFB and SHA-1.
     */
    @Test
    public void sha1DesOfbTest() {
        config.addSetting("HashType", "SHA-1");
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "OFB");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with DES CBC and SHA-1.
     */
    @Test
    public void sha1DesCbcTest() {
        config.addSetting("HashType", "SHA-1");
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "CBC");
        UtilsForTest.testHash(config, times);
    }

    /* SHA-224 */

    /**
     * This method tests hashing with AES OFB and SHA-224.
     */
    @Test
    public void sha224AesOfbTest() {
        config.addSetting("HashType", "SHA-224");
        config.addSetting("Type", "AES");
        config.addSetting("BlockModus", "OFB");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with AES CBC and SHA-224.
     */
    @Test
    public void sha224AesCbcTest() {
        config.addSetting("HashType", "SHA-224");
        config.addSetting("Type", "AES");
        config.addSetting("BlockModus", "CBC");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with DES OFB and SHA-224.
     */
    @Test
    public void sha224DesOfbTest() {
        config.addSetting("HashType", "SHA-224");
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "OFB");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with DES CBC and SHA-224.
     */
    @Test
    public void sha224DesCbcTest() {
        config.addSetting("HashType", "SHA-224");
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "CBC");
        UtilsForTest.testHash(config, times);
    }

    /* SHA-256 */

    /**
     * This method tests hashing with AES OFB and SHA-256.
     */
    @Test
    public void sha256AesOfbTest() {
        config.addSetting("HashType", "SHA-256");
        config.addSetting("Type", "AES");
        config.addSetting("BlockModus", "OFB");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with AES CBC and SHA-256.
     */
    @Test
    public void sha256AesCbcTest() {
        config.addSetting("HashType", "SHA-256");
        config.addSetting("Type", "AES");
        config.addSetting("BlockModus", "CBC");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with DES OFB and SHA-256.
     */
    @Test
    public void sha256DesOfbTest() {
        config.addSetting("HashType", "SHA-256");
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "OFB");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with DES CBC and SHA-256.
     */
    @Test
    public void sha256DesCbcTest() {
        config.addSetting("HashType", "SHA-256");
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "CBC");
        UtilsForTest.testHash(config, times);
    }

    /* SHA-384 */

    /**
     * This method tests hashing with AES OFB and SHA-384.
     */
    @Test
    public void sha384AesOfbTest() {
        config.addSetting("HashType", "SHA-384");
        config.addSetting("Type", "AES");
        config.addSetting("BlockModus", "OFB");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with AES CBC and SHA-384.
     */
    @Test
    public void sha384AesCbcTest() {
        config.addSetting("HashType", "SHA-384");
        config.addSetting("Type", "AES");
        config.addSetting("BlockModus", "CBC");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with DES OFB and SHA-384.
     */
    @Test
    public void sha384DesOfbTest() {
        config.addSetting("HashType", "SHA-384");
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "OFB");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with DES CBC and SHA-384.
     */
    @Test
    public void sha384DesCbcTest() {
        config.addSetting("HashType", "SHA-384");
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "CBC");
        UtilsForTest.testHash(config, times);
    }

    /* SHA-512*/

    /**
     * This method tests hashing with AES OFB and SHA-512.
     */
    @Test
    public void sha512AesOfbTest() {
        config.addSetting("HashType", "SHA-512");
        config.addSetting("Type", "AES");
        config.addSetting("BlockModus", "OFB");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with AES CBC and SHA-512.
     */
    @Test
    public void sha512AesCbcTest() {
        config.addSetting("HashType", "SHA-512");
        config.addSetting("Type", "AES");
        config.addSetting("BlockModus", "CBC");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with DES OFB and SHA-512.
     */
    @Test
    public void sha512DesOfbTest() {
        config.addSetting("HashType", "SHA-512");
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "OFB");
        UtilsForTest.testHash(config, times);
    }

    /**
     * This method tests hashing with DES OFB and SHA-512.
     */
    @Test
    public void sha512DesCbcTest() {
        config.addSetting("HashType", "SHA-512");
        config.addSetting("Type", "DES");
        config.addSetting("BlockModus", "CBC");
        UtilsForTest.testHash(config, times);
    }
}
