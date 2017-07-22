package app.model.cryptomodes.des;

import app.model.Encryptor;

import javax.crypto.*;
import java.security.SecureRandom;


public abstract class DesEncryptor extends Encryptor {

    public DesEncryptor(String padding) throws Exception {
        super(padding);
        KeyGenerator keyGen = KeyGenerator.getInstance("DES", "BC");
        SecureRandom random = SecureRandom.getInstanceStrong();
        keyGen.init(54, random);
        setKey(keyGen.generateKey());
    }

    @Override
    public abstract byte[] encrypt(byte[] content) throws Exception;
}
