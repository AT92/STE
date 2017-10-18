package app.model.cryptomodes.aes;


import app.model.Encryptor;

import javax.crypto.KeyGenerator;
import java.security.SecureRandom;

/**
 * This is an abstract class for an AES encryptor.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public abstract class AesEncryptor extends Encryptor {
    /**
     * Padding algorithm this encryptor uses.
     */
    private final String padding;

    /**
     * This is a constructor for the AES encryptor.
     * @param keysize, the keysize for this encryptor(128, 192 or 256)
     * @param padding, the padding, it uses.
     * @param hashType, the hashtype, this encryptor uses.
     * @throws Exception, is thrown, when something bad happens.
     */
    protected AesEncryptor(int keysize, String padding, String hashType) throws Exception {
        super(hashType);
        this.padding = padding;
        //possible keysizes for AES
        System.out.println(keysize);
        assert keysize == 128 || keysize == 192 || keysize == 256;
        KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC");
        SecureRandom random = SecureRandom.getInstanceStrong();
        //initializing the keygenerator
        keyGen.init(keysize, random);
        //setting the random generated key
        setKey(keyGen.generateKey());
    }

    /**
     * This is a getter method for the padding.
     * @return the padding, this encryptor uses.
     */
    protected String getPadding() {
        return padding;
    }


}
