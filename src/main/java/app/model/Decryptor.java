package app.model;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * This is an abstract class for any decryptor.
 * This class decrypts an encrypted message and removes the added hash information from it.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public abstract class Decryptor {
    /**
     * The key for the decryption.
     */
    private Key key;
    /**
     * The digest of the content.
     */
    private final MessageDigest hash;
    /**
     * Detects if the message was modified.
     */
    private boolean messageValid = true;

    /**
     * This is a default constructor for any decryptor.
     *
     * @param hashType, the type of hashing, which was used for encrypting
     * @throws Exception, is thrown when there is no provider or no such algorithm
     */
    protected Decryptor(String hashType) throws Exception {
        this.hash = MessageDigest.getInstance(hashType, "BC");
    }

    /**
     * This method decrypts the input and returns the decrypted information as a byte array.
     * @param input, the information, which should be decrypted.
     * @return the decrypted information as a byte array.
     * @throws Exception, is thrown, when something bad happend
     */
    protected abstract byte[] decrypt(byte[] input) throws Exception;

    /**
     * This method decrypts a string and returns a string.
     * @param content, the content, which should be decrypted as a string.
     * @return the decrypted content as a string
     * @throws Exception, is thrown when something bad happened.
     */
    public String decryptAsString(String content) throws Exception {
        return new String(decrypt(Base64.getDecoder().decode(content)), StandardCharsets.US_ASCII)
                .replaceAll("\\u0000+$", "");
    }

    /**
     * This is the setter method for the key.
     * @param key, which is used for the decryption.
     */
    protected void setKey(Key key) {
        this.key = key;
    }

    /**
     * Getter method for the key.
     * @return the key, this decryptor works with.
     */
    protected Key getKey() {
        return key;
    }

    /**
     * Getter method for the messageValid variable.
     * @return true, if the message was not modified, false otherwise.
     */
    public boolean getIsValidMessage() {
        return messageValid;
    }

    /**
     * This method removes the hashinformation from the content.
     * @param output, the decrypted content, with hash information
     * @return the decrypted content without any hash information.
     */
    protected byte[] removeHash(byte[] output) {
        //getting the length of the plain text
        int messageLength = output.length - hash.getDigestLength();
        //updating the hash
        hash.update(output, 0, output.length - hash.getDigestLength());
        //creating an array for the plain text
        byte[] realOutput = new byte[messageLength];
        //copying the plaintext to realOutput
        System.arraycopy(output, 0, realOutput, 0, messageLength);
        //validating the plaintext with hash
        messageValid = isValid(output);
        return realOutput;
    }

    /**
     * This method removes the hashinformation from the content.
     * @param output, the decrypted content, with hash information
     * @param ctLength, the length of the plaintext without things like padding and added blocks
     * @return the decrypted content without any hash information.
     */
    protected byte[] removeHash(byte[] output, int ctLength) {
        //new array with the length of plaintext without padding
        byte[] outputNoPadding = new byte[ctLength];
        //copying the output to the array outputNoPadding
        System.arraycopy(output, 0, outputNoPadding, 0, ctLength);
        //Getting the length of the content without hash information
        int messageLength = outputNoPadding.length - hash.getDigestLength();
        //updating the hash information
        hash.update(outputNoPadding, 0, outputNoPadding.length - hash.getDigestLength());
        //array for the plaintext
        byte[] realOutput = new byte[messageLength];
        //copying the plaintext to a new array
        System.arraycopy(outputNoPadding, 0, realOutput, 0, messageLength);
        //checking, if the content was modified
        messageValid = isValid(outputNoPadding);
        return realOutput;
    }

    /**
     * This method checks, if the content was modified.
     * @param output
     * @return
     */
    private boolean isValid(byte[] output) {
        int messageLength = output.length - hash.getDigestLength();
        //Getting the hash from the content
        byte[] messageHash = new byte[hash.getDigestLength()];
        System.arraycopy(output, messageLength, messageHash, 0, messageHash.length);
        //validating the hash information
        return MessageDigest.isEqual(hash.digest(), messageHash);
    }

}
