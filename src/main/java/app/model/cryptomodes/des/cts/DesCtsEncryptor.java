package app.model.cryptomodes.des.cts;


import app.model.cryptomodes.des.cbc.DesCbcEncryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

public class DesCtsEncryptor extends DesCbcEncryptor {

    public DesCtsEncryptor(String padding) throws Exception {
        super(padding);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CTS/" + getPadding(), "BC");
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        iv = new IvParameterSpec(cipher.getIV());
        byte[] output = new byte[cipher.getOutputSize(iv.getIV().length + input.length)];
        int ctLength = cipher.update(iv.getIV(), 0, iv.getIV().length, output, 0);
        ctLength += cipher.update(input, 0, input.length, output, 0);
        cipher.doFinal(output, ctLength);
        return output;
    }
}
