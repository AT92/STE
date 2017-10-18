package app.model.cryptomodes.des;

import app.model.Encryptor;

import javax.crypto.KeyGenerator;
import java.security.SecureRandom;

/**
 * This is an abstract class for any DES decryptor.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public abstract class DesEncryptor extends Encryptor {
    /**
     * The padding this decryptor uses
     */
    private String padding;

    /**
     * This method creates a new DES decryptor.
     * @param padding, the padding this decryptor uses.
     * @param hashType, the type of hash this decryptor uses.
     * @throws Exception, is thrown, when something bad happens.
     */
    protected DesEncryptor(String padding, String hashType) throws Exception {
        super(hashType);
        this.padding = padding;
        //Generating a random key
        KeyGenerator keyGen = KeyGenerator.getInstance("DES", "BC");
        SecureRandom random = SecureRandom.getInstanceStrong();
        keyGen.init(54, random);
        setKey(keyGen.generateKey());
    }

    /**
     * Getter method for the padding.
     * @return the padding, this decryptor uses.
     */
    protected String getPadding() {
        return padding;
    }
}
