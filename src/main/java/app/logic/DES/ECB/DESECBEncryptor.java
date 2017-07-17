package app.logic.DES.ECB;


import app.logic.DES.DESEncryptor;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public final class DESECBEncryptor extends DESEncryptor {
    public DESECBEncryptor(String padding) throws NoSuchProviderException, NoSuchAlgorithmException {
        super(padding);
    }

    public byte[] encrypt(byte[] input) throws NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, InvalidKeyException, ShortBufferException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("DES/ECB/" + getPadding(), "BC");
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        cipher.doFinal(output, ctLength);
        return output;
    }
}
