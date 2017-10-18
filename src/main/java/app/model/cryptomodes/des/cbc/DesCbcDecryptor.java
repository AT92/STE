package app.model.cryptomodes.des.cbc;


import app.model.cryptomodes.des.DesDecryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

/**
 * This class stand for a DES decryptor, which works
 * in CBC mode.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public class DesCbcDecryptor extends DesDecryptor {
    /**
     * The initialization vector
     */
    protected IvParameterSpec iv;

    /**
     * This is a constructor for the DES CBC decryptor
     * @param padding, the padding this decryptor uses.
     * @param key, the key this decryptor uses.
     * @param IV, the initialization vector for this decryptor.
     * @param hashType, the type of hash
     * @throws Exception, is thrown, when something bad happens
     */
    public DesCbcDecryptor(String padding, String key, byte[] IV, String hashType) throws Exception {
        super(padding, key, hashType);
        iv =  new IvParameterSpec(IV);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        //Getting an instance of the cipher from the bouncy castle provider
        Cipher cipher = Cipher.getInstance("DES/CBC/" + getPadding(), "BC");
        //initialization of the cipher by setting the key, the iv and the decryption mode
        cipher.init(Cipher.DECRYPT_MODE, getKey(), iv);
        //creatin an output array with the size of plaintext + iv + hash
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        //decrypting the first blocks
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //decrypting the last block
        ctLength += cipher.doFinal(output, ctLength);
        //getting the array with the size of plaintext + hash
        byte[] plaintext = new byte[ctLength - iv.getIV().length];
        //copying decrypted plaintext + hash to the plaintext array
        System.arraycopy(output, iv.getIV().length, plaintext, 0, plaintext.length);
        //removing the hash
        return removeHash(plaintext);
    }

    /**
     * Getter method for the initialization vector.
     * @return the initialization vector.
     */
    protected IvParameterSpec getIVParamSpec() {
        return iv;
    }
}
