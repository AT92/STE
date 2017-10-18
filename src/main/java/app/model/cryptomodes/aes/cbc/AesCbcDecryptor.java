package app.model.cryptomodes.aes.cbc;


import app.model.cryptomodes.aes.AesDecryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

/**
 * This class is an AES decryptor, which works in the CBC mode.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public class AesCbcDecryptor extends AesDecryptor {
    /**
     * The initialization vector, this decryptor uses.
     */
    private final IvParameterSpec iv;

    /**
     * This method creates an instance of AesCbcDecryptor class.
     * @param padding, the padding, this decryptor uses.
     * @param key, the key, this decryptor uses for decrypting the information
     * @param IV, the initialization vector.
     * @param hashType, the type of the hash, which was appended to the content
     * @throws Exception, is thrown, when something bad happened.
     */
    public AesCbcDecryptor(final String padding, final String key, final byte[] IV, String hashType) throws Exception {
        super(padding, key, hashType);
        this.iv =  new IvParameterSpec(IV);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        //Getting and instance from the bouncy castle provider
        Cipher cipher = Cipher.getInstance("AES/CBC/" + getPadding(), "BC");
        //initializing the cipher, by setting the decryption mode, passing the key and the iv
        cipher.init(Cipher.DECRYPT_MODE, getKey(), getIvParamSpec());
        //Getting the size of the output
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        //decrypting all full blocks
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //decrypting the last block and removing the padding
        ctLength += cipher.doFinal(output, ctLength);
        //creating and filling the array with the length of the input - iv
        byte[] plaintext = new byte[ctLength - getIvParamSpec().getIV().length];
        System.arraycopy(output, getIvParamSpec().getIV().length, plaintext, 0, plaintext.length);
        return removeHash(plaintext);
    }

    /**
     * Getter method for the initialization vector.
     * @return the initialization vector.
     */
    protected IvParameterSpec getIvParamSpec() {
        return this.iv;
    }
}
