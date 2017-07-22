package app.model.cryptomodes.des.cbc;


import app.model.cryptomodes.des.DesDecryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

public class DesCbcDecryptor extends DesDecryptor {
    protected IvParameterSpec iv;

    public DesCbcDecryptor(String padding, String key, byte[] IV) {
        super(padding, key);
        iv =  new IvParameterSpec(IV);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/" + getPadding(), "BC");
        cipher.init(Cipher.DECRYPT_MODE, getKey(), iv);
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        ctLength += cipher.doFinal(output, ctLength);
        byte[] plaintext = new byte[ctLength - iv.getIV().length];
        System.arraycopy(output, iv.getIV().length, plaintext, 0, plaintext.length);
        return plaintext;
    }

    protected IvParameterSpec getIVParamSpec() {
        return iv;
    }
}
