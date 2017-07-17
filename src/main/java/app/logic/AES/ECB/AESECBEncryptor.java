package app.logic.AES.ECB;

import app.logic.AES.AESEncryptor;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;



public final class AESECBEncryptor extends AESEncryptor {
    public AESECBEncryptor(int keyLength, String padding) throws NoSuchProviderException,
            NoSuchAlgorithmException {
        super(keyLength, padding);
    }

    @Override
    public byte[] encrypt(byte[] input) throws NoSuchAlgorithmException, NoSuchProviderException,
                                                    NoSuchPaddingException, InvalidKeyException, ShortBufferException,
                                                    BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/ECB/" + getPadding(), "BC");
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        cipher.doFinal(output, ctLength);
        return output;
    }
}
