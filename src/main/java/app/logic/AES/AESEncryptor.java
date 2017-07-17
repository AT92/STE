package app.logic.AES;


import app.logic.Encryptor;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public abstract class AESEncryptor extends Encryptor {

    public AESEncryptor(int keyLength, String padding) throws NoSuchProviderException, NoSuchAlgorithmException {
        super(padding);
        if (keyLength != 128 && keyLength != 192 && keyLength != 256) {
            throw new IllegalStateException("Wrong keysize");
        }
        KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC");
        keyGen.init(keyLength);
        setKey(keyGen.generateKey());
    }

    @Override
    public abstract byte[] encrypt(byte[] content) throws NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, InvalidKeyException, ShortBufferException,
            BadPaddingException, IllegalBlockSizeException;
}
