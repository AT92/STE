package app.pbetests;


import app.CryptoTest;
import app.UtilsForTest;
import org.junit.Test;

/**
 * This class test the password based encryption/decryption
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class PBETest extends CryptoTest {


    /**
     * This method checks the PBEWithSHAAnd40BitRC4 encryption/decryption
     * for equality.
     */
    @Test
    public void testPBEWithSHAAnd40BitRC4() {
        config.addSetting("Type", "ARC4");
        config.addSetting("PbeModus", "PBEWithSHAAnd40BitRC4");
        UtilsForTest.testConfigPBE(config, times);
    }

    /**
     * This method checks the PBEWithSHAAnd40BitRC4 encryption/decryption
     * with a false pass.
     */
    @Test
    public void testPBEWithSHAAnd40BitRC4FalsePass() {
        config.addSetting("Type", "ARC4");
        config.addSetting("PbeModus", "PBEWithSHAAnd40BitRC4");
        UtilsForTest.testFalsePass(config, times);
    }

    /**
     * This method checks the PBEWithSHAAnd128BitAES_CBC_BC encryption/decryption
     * for equality.
     */
    @Test
    public void testPBEWithSHAAnd128BitAES_CBC_BC() {
        config.addSetting("PbeModus", "PBEWithSHAAnd128BitAES-CBC-BC");
        UtilsForTest.testConfigPBE(config, times);
    }

    /**
     * This method checks the PBEWithSHAAnd128BitAES_CBC_BC encryption/decryption
     * with a false pass.
     */
    @Test
    public void testPBEWithSHAAnd128BitAES_CBC_BCFalsePass() {
        config.addSetting("PbeModus", "PBEWithSHAAnd128BitAES-CBC-BC");
        UtilsForTest.testFalsePass(config, times);
    }

    /**
     * This method checks the PBEWithMD5AndDES encryption/decryption
     * for equality.
     */
    @Test
    public void testPBEWithMD5AndDES() {
        config.addSetting("PbeModus", "PBEWithMD5AndDES");
        UtilsForTest.testConfigPBE(config, times);
    }

    /**
     * This method checks the PBEWithMD5AndDES encryption/decryption
     * with a false pass.
     */
    @Test
    public void testPBEWithMD5AndDESFalsePass() {
        config.addSetting("PbeModus", "PBEWithMD5AndDES");
        UtilsForTest.testFalsePass(config, times);
    }
}
