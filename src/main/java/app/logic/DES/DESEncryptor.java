package app.logic.DES;

import app.logic.Encryptor;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public abstract class DESEncryptor extends Encryptor {

    public DESEncryptor(String padding)
            throws NoSuchProviderException, NoSuchAlgorithmException {
        super(padding);
        KeyGenerator keyGen = KeyGenerator.getInstance("DES", "BC");
        keyGen.init(54);
        setKey(keyGen.generateKey());
    }

    @Override
    public abstract byte[] encrypt(byte[] content) throws NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, InvalidKeyException,
            ShortBufferException, BadPaddingException, IllegalBlockSizeException;
}
