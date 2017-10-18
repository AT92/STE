package app.model.cryptomodes.aes;


import app.model.Decryptor;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * This is an abstract class for an AES decryptor.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public abstract class AesDecryptor extends Decryptor {
    /**
     * The padding algorithm, this decryptor is using.
     */
    private String padding;

    /**
     * This is a constructor for an AES decryptor object.
     * @param padding, the padding algorithm for this decryptor
     * @param key, the key, this decryptor is using for decrypting the content.
     * @param hashType, the hashtype, which is used for thproof of intigrety of the content
     * @throws Exception, is thrown, when something bad happens.
     */
    protected AesDecryptor(String padding, String key, String hashType) throws Exception {
        super(hashType);
        this.padding = padding;
        byte[] keyB = Base64.getDecoder().decode(key);
        //the keys should be 128, 192 or 256 bit long.
        assert keyB.length == 16 || keyB.length == 24 || keyB.length == 32;
        setKey(new SecretKeySpec(keyB, "AES"));
    }

    /**
     * Getter method for the padding, this encryptor is using.
     * @return padding of this decryptor.
     */
    protected String getPadding() {
        return padding;
    }
}
