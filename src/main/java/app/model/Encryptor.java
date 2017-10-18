package app.model;


import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * This is an abstract class for any encryptor. It encrypts the information.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public abstract class Encryptor {
    /**
     * The key for the encryption.
     */
    private Key key;
    /**
     * The hash for the encryption.
     */
    private final MessageDigest hash;

    /**
     * This is a constructor for the encryptor.
     * @param hashType, the hashtype , which is used by this encryptor.
     * @throws Exception, is thrown, when there is no such provider or such algorithm availible.
     */
    protected Encryptor(String hashType) throws Exception {
        this.hash = MessageDigest.getInstance(hashType, "BC");
    }

    /**
     * This method encrypts the passed content.
     * @param content, the content, which should be encrypted.
     * @return the encrypted content as a byte array.
     * @throws Exception, is thrown when the encryption could not work (there are a lot of reasons for this)
     */
    private byte[] encrypt(String content) throws Exception {
        return encrypt(content.getBytes(StandardCharsets.US_ASCII));
    }

    /**
     * This method encrypts the encrypted byte array to a string.
     * @param content, the content, which should be encrypted.
     * @return the encrypted content as a string
     * @throws Exception, is thrown, when something bad happened.
     */
    public String encryptAsString(String content) throws Exception {
        return Base64.getEncoder().encodeToString(encrypt(content));
    }

    /**
     * This method encrypts an byte array to a byte array.
     * @param input, the input, which should be encrypted.
     * @return the encrypted input as a byte array.
     * @throws Exception, is thrown, when something bad happened,
     */
    protected abstract byte[] encrypt(byte[] input) throws Exception;

    /**
     * Returns the key in the string representation.
     * @return the key as a string.
     */
    protected String getKeyAsString() {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * Getter method for the key.
     * @return the key of this encryptor.
     */
    protected Key getKey() {
        return key;
    }

    /**
     * Setter method for the key.
     * @param key, the key for this encryptor.
     */
    protected void setKey(Key key) {
        this.key = key;
    }

    /**
     * Getting the length of the array, where the encrypted content is stored
     * with hash and padding.
     * @param cipher, the cipher, which encrypts the content.
     * @param input, the input, which should be encrypted.
     * @return the length of the encrypted array with padding and hash.
     */
    protected byte[] getBytesForCipher(Cipher cipher, byte[] input) {
        return new byte[cipher.getOutputSize(input.length + hash.getDigestLength())];
    }

    /**
     * This method updates the hash
     * @param input, the input, the hash updating depends on
     */
    protected void updateHash(byte[] input) {
        hash.update(input);
    }

    /**
     * Getter method for the digest.
     * @return the digest.
     */
    protected byte[] getDigest() {
        return hash.digest();
    }

    /**
     * This method updates the configuration (eg. adding the key, salt or iv)
     * @param config, the configuration, which should be updated
     * @return the updated configuration
     */
    public abstract Configuration updateConfiguration(Configuration config);
}
