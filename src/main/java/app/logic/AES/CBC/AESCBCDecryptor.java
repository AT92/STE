package app.logic.AES.CBC;


import app.logic.AES.AESDecryptor;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public final class AESCBCDecryptor extends AESDecryptor {
    private IvParameterSpec iv;

    public AESCBCDecryptor(String padding, String key) {
        super(padding, key);
        byte[] ivB = Base64.getDecoder().decode(key.substring(key.length() - 24));
        byte[] realKey = Base64.getDecoder().decode(key.substring(0, key.length() - 24));
        setKey(new SecretKeySpec(realKey, "AES"));
        iv =  new IvParameterSpec(ivB);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/" + getPadding(), "BC");
        cipher.init(Cipher.DECRYPT_MODE, getKey(), iv);
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        ctLength += cipher.doFinal(output, ctLength);
        byte[] plaintext = new byte[ctLength - iv.getIV().length];
        System.arraycopy(output, iv.getIV().length, plaintext, 0, plaintext.length);
        return plaintext;
    }


}
