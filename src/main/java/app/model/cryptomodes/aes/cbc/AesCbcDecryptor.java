package app.model.cryptomodes.aes.cbc;


import app.model.cryptomodes.aes.AesDecryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

public class AesCbcDecryptor extends AesDecryptor {
    private final IvParameterSpec iv;

    public AesCbcDecryptor(final String padding, final String key, final byte[] IV) {
        super(padding, key);
        this.iv =  new IvParameterSpec(IV);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/" + getPadding(), "BC");
        cipher.init(Cipher.DECRYPT_MODE, getKey(), getIvParamSpec());
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        ctLength += cipher.doFinal(output, ctLength);
        byte[] plaintext = new byte[ctLength - getIvParamSpec().getIV().length];
        System.arraycopy(output, getIvParamSpec().getIV().length, plaintext, 0, plaintext.length);
        return plaintext;
    }

    protected IvParameterSpec getIvParamSpec() {
        return this.iv;
    }
}
