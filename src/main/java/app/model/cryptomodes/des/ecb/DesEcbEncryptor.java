package app.model.cryptomodes.des.ecb;


import app.model.Configuration;
import app.model.cryptomodes.des.DesEncryptor;

import javax.crypto.*;

public final class DesEcbEncryptor extends DesEncryptor {
    public DesEcbEncryptor(String padding) throws Exception {
        super(padding);
    }

    public byte[] encrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/ECB/" + getPadding(), "BC");
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
