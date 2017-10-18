package app.model.cryptofactories;


import app.model.Configuration;
import app.model.Encryptor;
import app.model.cryptomodes.aes.cbc.AesCbcEncryptor;
import app.model.cryptomodes.aes.cts.AesCtsEncryptor;
import app.model.cryptomodes.aes.ecb.AesEcbEncryptor;
import app.model.cryptomodes.aes.gcm.AesGcmEncryptor;
import app.model.cryptomodes.aes.ofb.AesOfbEncryptor;
import app.model.cryptomodes.des.cbc.DesCbcEncryptor;
import app.model.cryptomodes.des.cts.DesCtsEncryptor;
import app.model.cryptomodes.des.ecb.DesEcbEncryptor;
import app.model.cryptomodes.des.ofb.DesOfbEncryptor;
import app.model.cryptomodes.passwordbasedcrypto.KeyCreatorPBE;
import app.model.cryptomodes.passwordbasedcrypto.pbewithmd5anddes.PBEWithMD5AndDESEncryptor;
import app.model.cryptomodes.passwordbasedcrypto.pbewithshaand128bitaescbcbc.PBEWithSHAAnd128BitAES_CBC_BCEncryptor;
import app.model.cryptomodes.passwordbasedcrypto.pbewithshaand40bitrc4.PBEWithSHAAnd40BitRC4Encryptor;
import app.model.cryptomodes.rsa.RSAEncryptor;

import javax.crypto.SecretKey;

/**
 * This class works as a factory for the encryptors. Depending on the passed configuration
 * it selects the right encryptor and initializes it.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class EncryptorFactory {
    /**
     * The configuration for the encryptor.
     * Is stored for storing the salt by PBEs.
     */
    private static Configuration config;

    /**
     * This method selects an encryptor according to the passed configuration,
     * and initializes it.
     * @param config, the configuration of the encryptor.
     * @return the encryptor, which was configured.
     * @throws Exception, thrown when a encryptor could not be initialized, or if an error occured.
     */
    public static Encryptor getEncryptor(Configuration config) throws Exception {
        String type = config.getSetting("Type");
        String hashType = config.getSetting("HashType");
        if (type.equals("RSA")) {
            return new RSAEncryptor(hashType, config.getSetting("PublicKey"));
        }
        String padding = config.getSetting("Padding");
        String blockModus = config.getSetting("BlockModus");
        //deafult keysize for AES.
        int keySize = 128;
        if (type.equals("AES")) {
            if (config.settingExist("Keysize")) {
                keySize = Integer.parseInt(config.getSetting("Keysize"));
            }
            switch (blockModus) {
                case "ECB": return new AesEcbEncryptor(keySize, padding, hashType);
                case "CBC": return new AesCbcEncryptor(keySize, padding, hashType);
                case "CTS": return new AesCtsEncryptor(keySize, padding, hashType);
                case "OFB": return new AesOfbEncryptor(keySize, hashType);
                case "GCM": return new AesGcmEncryptor(keySize, hashType);
                default: throw new IllegalStateException();
            }
        } else if (type.equals("DES")) {
            switch (blockModus) {
                case "ECB": return new DesEcbEncryptor(padding, hashType);
                case "CBC": return new DesCbcEncryptor(padding, hashType);
                case "CTS": return new DesCtsEncryptor(padding, hashType);
                case "OFB": return new DesOfbEncryptor(hashType);
                default: throw new IllegalStateException();
            }
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * This method creates the key out of the passed configuration and the password and returns
     * the encryptor, according to the configuration.
     * @param configuration, the configuration for the decryptor.
     * @param pass, the password the user put in.
     * @return the decryptor configured by the passed configuration object.
     * @throws Exception, is thrown, when a decryptor could not be initialized or the key
     * could not be restored.
     */
    public static Encryptor getEncryptor(Configuration configuration, String pass) throws Exception {
        //Creating the PBE-Key
        KeyCreatorPBE keyCreatorPBE = new KeyCreatorPBE(configuration, pass);
        SecretKey key = keyCreatorPBE.createKey();
        //Updating the configuration, by adding the salt to it.
        config = keyCreatorPBE.getNewConfiguration();
        //Selecting the encryptor.
        switch (config.getSetting("PbeModus")) {
            case "PBEWithSHAAnd128BitAES-CBC-BC": return new PBEWithSHAAnd128BitAES_CBC_BCEncryptor(config.getSetting("HashType"), key);
            case "PBEWithMD5AndDES": return new PBEWithMD5AndDESEncryptor(config.getSetting("HashType"), key);
            case "PBEWithSHAAnd40BitRC4": return new PBEWithSHAAnd40BitRC4Encryptor(config.getSetting("HashType"), key);
            default: throw new IllegalStateException();
        }
    }

    /**
     * Getter method for the updated configuration with salt.
     * @return the configuration with salt.
     */
    public Configuration getConfig() {
        assert config != null;
        return config;
    }
}
