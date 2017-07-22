package app.model.cryptomodes.aes;


import app.model.Encryptor;

import javax.crypto.*;
import java.security.SecureRandom;


public abstract class AesEncryptor extends Encryptor {

    public AesEncryptor(int keysize, String padding) throws Exception {
        super(padding);
        assert keysize == 128 || keysize == 192 || keysize == 256;
        KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC");
        SecureRandom random = SecureRandom.getInstanceStrong();
        keyGen.init(keysize, random);
        setKey(keyGen.generateKey());
    }

    @Override
    public abstract byte[] encrypt(byte[] content) throws Exception;
}
