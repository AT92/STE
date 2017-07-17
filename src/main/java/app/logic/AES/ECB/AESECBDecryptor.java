package app.logic.AES.ECB;


import app.logic.AES.AESDecryptor;


import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public final class AESECBDecryptor extends AESDecryptor {
    public AESECBDecryptor(String padding, String key) {
        super(padding, key);
    }

    public byte[] decrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/" + getPadding(), "BC");
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        cipher.doFinal(output, ctLength);
        return output;
    }
}
