package app.model.cryptomodes.des.cbc;


import app.model.Configuration;
import app.model.cryptomodes.des.DesEncryptor;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

public class DesCbcEncryptor extends DesEncryptor {
    protected IvParameterSpec iv;

    public DesCbcEncryptor(String padding) throws Exception {
        super(padding);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/" + getPadding(), "BC");
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        iv = new IvParameterSpec(cipher.getIV());
        byte[] output = new byte[cipher.getOutputSize(iv.getIV().length + input.length)];
        int ctLength = cipher.update(iv.getIV(), 0, iv.getIV().length, output, 0);
        ctLength += cipher.update(input, 0, input.length, output, 0);
        cipher.doFinal(output, ctLength);
        return output;
    }

    private String getIVAsString() {
        return Base64.getEncoder().encodeToString(iv.getIV());
    }

    public void setIVParamSpec(IvParameterSpec iv) {
        this.iv = iv;
    }

    public IvParameterSpec getIVParamSpec() {
        return iv;
    }

    @Override
    public Configuration updateConfiguration(Configuration config) {
        config.addSetting("IV", getIVAsString());
        config.addSetting("Key", getKeyAsString());
        return config;
    }
}
