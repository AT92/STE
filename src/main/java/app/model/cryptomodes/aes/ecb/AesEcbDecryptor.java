package app.model.cryptomodes.aes.ecb;


import app.model.cryptomodes.aes.AesDecryptor;

import javax.crypto.Cipher;

/**
 * This class creates an AES decryptor with ECB mode.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class AesEcbDecryptor extends AesDecryptor {
    /**
     * This method creates an instance of AES decryptor in ECB mode.
     * @param padding, the padding this decryptor uses.
     * @param key, the key this decryptor uses.
     * @param hashType, the type of hash, this decryptor uses.
     * @throws Exception, is thrown, when something bad happened.
     */
    public AesEcbDecryptor(String padding, String key, String hashType) throws Exception {
        super(padding, key, hashType);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        //Getting an instance from the bouncy castle provider
        Cipher cipher = Cipher.getInstance("AES/ECB/" + getPadding(), "BC");
        //initializing the cipher, by setting the decryption mode, passing the key and the iv
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        //getting the size of the output from the cipher
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        //decrypting all full blocks
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //decrypting the final block with padding
        ctLength += cipher.doFinal(output, ctLength);
        return removeHash(output, ctLength);
    }
}
