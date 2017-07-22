package app.model.cryptomodes.aes.ecb;

import app.model.cryptomodes.aes.AesEncryptor;
import app.model.Configuration;

import javax.crypto.*;


public final class AesEcbEncryptor extends AesEncryptor {
    public AesEcbEncryptor(int keyLength, String padding) throws Exception {
        super(keyLength, padding);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/" + getPadding(), "BC");
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        cipher.doFinal(output, ctLength);
        return output;
    }

    @Override
    public Configuration updateConfiguration(Configuration config) {
        config.addSetting("Key", getKeyAsString());
        return config;
    }
}
