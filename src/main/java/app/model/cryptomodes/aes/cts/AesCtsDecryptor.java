package app.model.cryptomodes.aes.cts;


import app.model.cryptomodes.aes.cbc.AesCbcDecryptor;

import javax.crypto.Cipher;

/**
 * This class creates an AES decryptor with CTS mode.
 * It extends the CBC Decryptor, becauese CBC is also using the iv.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class AesCtsDecryptor extends AesCbcDecryptor {

    /**
     * This method creates an instance of AesCtcDecryptor class.
     * @param padding, the padding, this decryptor uses.
     * @param key, the key, this decryptor uses for decrypting the information
     * @param IV, the initialization vector.
     * @param hashType, the type of the hash, which was appended to the content
     * @throws Exception, is thrown, when something bad happened.
     */
    public AesCtsDecryptor(String padding, String key, byte[] IV, String hashType) throws Exception {
        super(padding, key, IV, hashType);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        //Getting an instance from the bouncy castle provider
        Cipher cipher = Cipher.getInstance("AES/CTS/" + getPadding(), "BC");
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
}
