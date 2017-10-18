package app.model.cryptofactories;


import app.model.Configuration;
import app.model.Decryptor;
import app.model.cryptomodes.aes.cbc.AesCbcDecryptor;
import app.model.cryptomodes.aes.cts.AesCtsDecryptor;
import app.model.cryptomodes.aes.ecb.AesEcbDecryptor;
import app.model.cryptomodes.aes.gcm.AesGcmDecryptor;
import app.model.cryptomodes.aes.ofb.AesOfbDecryptor;
import app.model.cryptomodes.des.cbc.DesCbcDecryptor;
import app.model.cryptomodes.des.cts.DesCtsDecryptor;
import app.model.cryptomodes.des.ecb.DesEcbDecryptor;
import app.model.cryptomodes.des.ofb.DesOfbDecryptor;
import app.model.cryptomodes.passwordbasedcrypto.KeyCreatorPBE;
import app.model.cryptomodes.passwordbasedcrypto.pbewithmd5anddes.PBEWithMD5AndDESDecryptor;
import app.model.cryptomodes.passwordbasedcrypto.pbewithshaand128bitaescbcbc.PBEWithSHAAnd128BitAES_CBC_BCDecryptor;
import app.model.cryptomodes.passwordbasedcrypto.pbewithshaand40bitrc4.PBEWithSHAAnd40BitRC4Decryptor;
import app.model.cryptomodes.rsa.RSADecryptor;

import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * This class works as a factory for the decryptors. Depending on the passed configuration
 * it selects the right decryptor and initializes it.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class DecryptorFactory {

    /**
     * This method selects a decryptor according to the passed configuration,
     * and initializes it.
     * @param config, the configuration of the decryptor.
     * @return the decryptor, which was configured.
     * @throws Exception, thrown when a decryptor could not be initialized, or if an error occured.
     */
    public static Decryptor getDecryptor(Configuration config) throws Exception {
        //Ecnryption type
        String type = config.getSetting("Type");
        String hashType = config.getSetting("HashType");
        if (type.equals("RSA")) {
            return new RSADecryptor(hashType, config.getSetting("PrivateKey"));
        }
        String key = config.getSetting("Key");
        String padding = config.getSetting("Padding");
        String blockModus = config.getSetting("BlockModus");
        byte[] IV = null;
        byte[] aad = null;
        if (config.settingExist("IV")) {
            IV = Base64.getDecoder().decode(config.getSetting("IV"));
        }
        if (config.settingExist("AAD")) {
            aad = Base64.getDecoder().decode(config.getSetting("AAD"));
        }
        if (type.equals("AES")) {
            switch (blockModus) {
                case "ECB": return new AesEcbDecryptor(padding, key, hashType);
                case "CBC": return new AesCbcDecryptor(padding, key, IV, hashType);
                case "CTS": return new AesCtsDecryptor(padding, key, IV, hashType);
                case "OFB": return new AesOfbDecryptor(key, IV, hashType);
                case "GCM": return new AesGcmDecryptor(key, IV, aad, hashType);
                default: throw new IllegalStateException();
            }
        } else if (type.equals("DES")) {
            switch (blockModus) {
                case "ECB": return new DesEcbDecryptor(padding, key, hashType);
                case "CBC": return new DesCbcDecryptor(padding, key, IV, hashType);
                case "CTS": return new DesCtsDecryptor(padding, key, IV, hashType);
                case "OFB": return new DesOfbDecryptor(key, IV, hashType);
                default: throw new IllegalStateException();
            }
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * This method restores the key out of the passed configuration and the password and returns
     * the decryptor, according to the configuration.
     * @param config, the configuration for the decryptor.
     * @param pass, the password the user put in.
     * @return the decryptor configured by the passed configuration object.
     * @throws Exception, is thrown, when a decryptor could not be initialized or the key
     * could not be restored.
     */
    public static Decryptor getDecryptor(Configuration config, String pass) throws Exception {
        //Restoring the key out of the configuration and the password.
        SecretKey key = new KeyCreatorPBE(config, pass).restoreKey();
        //Selecting the decryptor.
        switch (config.getSetting("PbeModus")) {
            case "PBEWithSHAAnd128BitAES-CBC-BC": return new PBEWithSHAAnd128BitAES_CBC_BCDecryptor(config.getSetting("HashType"), key);
            case "PBEWithMD5AndDES": return new PBEWithMD5AndDESDecryptor(config.getSetting("HashType"), key);
            case "PBEWithSHAAnd40BitRC4": return new PBEWithSHAAnd40BitRC4Decryptor(config.getSetting("HashType"), key);
            default: throw new IllegalStateException();
        }
    }
}
