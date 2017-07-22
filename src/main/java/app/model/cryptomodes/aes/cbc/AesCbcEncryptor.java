package app.model.cryptomodes.aes.cbc;


import app.model.cryptomodes.aes.AesEncryptor;
import app.model.Configuration;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

public class AesCbcEncryptor extends AesEncryptor {
    private IvParameterSpec iv;

    public AesCbcEncryptor(final int keysize, final String padding) throws Exception {
        super(keysize, padding);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/" + getPadding(), "BC");
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        setIVParamSpec(new IvParameterSpec(cipher.getIV()));

        byte[] output = new byte[cipher.getOutputSize(getIvParamSpec().getIV().length + input.length)];

        int ctLength = cipher.update(getIvParamSpec().getIV(), 0, getIvParamSpec().getIV().length, output, 0);
        ctLength += cipher.update(input, 0, input.length, output, ctLength);
        cipher.doFinal(output, ctLength);
        return output;
    }

    private String getIVAsString() {
        return Base64.getEncoder().encodeToString(iv.getIV());
    }

    protected void setIVParamSpec(IvParameterSpec iv) {
        this.iv = iv;
    }

    protected IvParameterSpec getIvParamSpec() {
        return iv;
    }

    @Override
    public Configuration updateConfiguration(final Configuration config) {
        config.addSetting("IV", getIVAsString());
        config.addSetting("Key", getKeyAsString());
        return config;
    }
}
