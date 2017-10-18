package app.model.cryptomodes.aes.ofb;


import app.model.cryptomodes.aes.cbc.AesCbcDecryptor;

import javax.crypto.Cipher;

/**
 * This class creates an AES decryptor with OFB mode.
 * It extends the OFB Decryptor, becauese OFB is also using the iv.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class AesOfbDecryptor extends AesCbcDecryptor {
    /**
     *
     * @param key, the key this decryptor is using
     * @param IV, the initialization vector for this decryptor
     * @param hashType, the type of hash, this decryptor is using
     * @throws Exception, is thrown, when something bad happens
     */
    public AesOfbDecryptor(String key, byte[] IV, String hashType) throws Exception {
        super("NoPadding", key, IV, hashType);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        //Getting an instance from the bouncy castle provider
        Cipher cipher = Cipher.getInstance("AES/OFB/" + getPadding(), "BC");
        //initializing the cipher, by setting the decryption mode, passing the key and the iv
        cipher.init(Cipher.DECRYPT_MODE, getKey(), getIvParamSpec());
        //Getting the size of the output
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        //decrypting the text
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //decrypting the last bits
        cipher.doFinal(output, ctLength);
        return removeHash(output);
    }
}
