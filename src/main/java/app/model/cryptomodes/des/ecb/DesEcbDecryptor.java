package app.model.cryptomodes.des.ecb;


import app.model.cryptomodes.des.DesDecryptor;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


public final class DesEcbDecryptor extends DesDecryptor {
    public DesEcbDecryptor(String padding, String key) {
        super(padding, key);
    }

    public byte[] decrypt(byte[] input) throws NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, InvalidKeyException, ShortBufferException,
            BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance("DES/ECB/" + getPadding(), "BC");
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        cipher.doFinal(output, ctLength);
        return output;
    }
}
