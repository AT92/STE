package app.model.cryptofactories;


import app.model.cryptomodes.aes.cbc.AesCbcEncryptor;
import app.model.cryptomodes.aes.cts.AesCtsEncryptor;
import app.model.cryptomodes.aes.ecb.AesEcbEncryptor;
import app.model.cryptomodes.aes.ofb.AesOfbEncryptor;
import app.model.Configuration;
import app.model.cryptomodes.des.cbc.DesCbcEncryptor;
import app.model.cryptomodes.des.cts.DesCtsEncryptor;
import app.model.cryptomodes.des.ecb.DesEcbEncryptor;
import app.model.Encryptor;
import app.model.cryptomodes.des.ofb.DesOfbEncryptor;


public final class EncryptorFactory {
    public static Encryptor getEncryptor(Configuration configuration) throws Exception {
        return getBlockEncryptor(configuration);
    }

    private static Encryptor getBlockEncryptor(Configuration config) throws Exception {
        String padding = config.getSetting("Padding");
        String blockModus = config.getSetting("BlockModus");
        String type = config.getSetting("Type");
        int keySize = 0;
        if (type.equals("AES")) {
            if (config.settingExist("Keysize")) {
                keySize = Integer.parseInt(config.getSetting("Keysize"));
            }
            switch (blockModus) {
                case "ECB": return new AesEcbEncryptor(keySize, padding);
                case "CBC": return new AesCbcEncryptor(keySize, padding);
                case "CTS": return new AesCtsEncryptor(keySize, padding);
                case "OFB": return new AesOfbEncryptor(keySize);
                default: throw new IllegalStateException();
            }
        } else if (type.equals("DES")) {
            switch (blockModus) {
                case "ECB": return new DesEcbEncryptor(padding);
                case "CBC": return new DesCbcEncryptor(padding);
                case "CTS": return new DesCtsEncryptor(padding);
                case "OFB": return new DesOfbEncryptor();
                default: throw new IllegalStateException();
            }
        } else {
            throw new IllegalStateException();
        }
    }
}
