package app.model.cryptomodes.des;

import app.model.Decryptor;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * This is an abstract class for a simple DES decryptor.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public abstract class DesDecryptor extends Decryptor {
    /**
     * The padding this decryptor uses.
     */
    private String padding;

    /**
     * This is a constructor for any DES decryptor
     * @param padding, the padding for this decryptor.
     * @param key, the key this decryptor is using.
     * @param hashType, the type of hash, this decryptor is using.
     * @throws Exception, is thrown, when something bad happens.
     */
    protected DesDecryptor(String padding, String key, String hashType) throws Exception {
        super(hashType);
        this.padding = padding;
        byte[] keyB = Base64.getDecoder().decode(key);
        setKey(new SecretKeySpec(keyB, "DES"));
    }

    /**
     * Getter method for the padding.
     * @return the padding, this decryptor is using.
     */
    protected String getPadding() {
        return padding;
    }
}
