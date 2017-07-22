package app.model.cryptofactories;


import app.model.cryptomodes.aes.cbc.AesCbcDecryptor;
import app.model.cryptomodes.aes.cts.AesCtsDecryptor;
import app.model.cryptomodes.aes.ecb.AesEcbDecryptor;
import app.model.cryptomodes.aes.ofb.AesOfbDecryptor;
import app.model.Configuration;
import app.model.cryptomodes.des.cbc.DesCbcDecryptor;
import app.model.cryptomodes.des.cts.DesCtsDecryptor;
import app.model.cryptomodes.des.ecb.DesEcbDecryptor;
import app.model.Decryptor;
import app.model.cryptomodes.des.ofb.DesOfbDecryptor;

import java.util.Base64;

public class DecryptorFactory {
    public static Decryptor getDecryptor(Configuration configuration) throws IllegalAccessException {
        return getBlockDecryptor(configuration);
    }


    private static Decryptor getBlockDecryptor(Configuration config) throws IllegalStateException {
        String padding = config.getSetting("Padding");
        String blockModus = config.getSetting("BlockModus");
        String type = config.getSetting("Type");
        String key = config.getSetting("Key");
        byte[] IV = null;
        if (config.settingExist("IV")) {
            IV = Base64.getDecoder().decode(config.getSetting("IV"));
        }
        if (type.equals("AES")) {
            switch (blockModus) {
                case "ECB": return new AesEcbDecryptor(padding, key);
                case "CBC": return new AesCbcDecryptor(padding, key, IV);
                case "CTS": return new AesCtsDecryptor(padding, key, IV);
                case "OFB": return new AesOfbDecryptor(key, IV);
                default: throw new IllegalStateException();
            }
        } else if (type.equals("DES")) {
            switch (blockModus) {
                case "ECB": return new DesEcbDecryptor(padding, key);
                case "CBC": return new DesCbcDecryptor(padding, key, IV);
                case "CTS": return new DesCtsDecryptor(padding, key, IV);
                case "OFB": return new DesOfbDecryptor(key, IV);
                default: throw new IllegalStateException();
            }
        } else {
            throw new IllegalStateException();
        }
    }
}
