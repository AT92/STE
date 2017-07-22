package app.model.cryptomodes.aes.cts;


import app.model.cryptomodes.aes.cbc.AesCbcEncryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

public final class AesCtsEncryptor extends AesCbcEncryptor {
    public AesCtsEncryptor(int keysize, String padding) throws Exception {
        super(keysize, padding);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CTS/" + getPadding(), "BC");
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        setIVParamSpec(new IvParameterSpec(cipher.getIV()));
        byte[] output = new byte[cipher.getOutputSize(getIvParamSpec().getIV().length + input.length)];
        int ctLength = cipher.update(getIvParamSpec().getIV(), 0, getIvParamSpec().getIV().length, output, 0);
        ctLength += cipher.update(input, 0, input.length, output, ctLength);
        cipher.doFinal(output, ctLength);
        return output;
    }
}
