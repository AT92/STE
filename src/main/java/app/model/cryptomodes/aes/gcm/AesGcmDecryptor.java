package app.model.cryptomodes.aes.gcm;


import app.model.cryptomodes.aes.AesDecryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;

/**
 * This class creates an AES decryptor with GCM mode.
 * It extends the GCM Decryptor, becauese GCM is also using the iv.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class AesGcmDecryptor extends AesDecryptor {
    /**
     * The ParameterSpec for the GCM mode
     */
    private GCMParameterSpec iv;
    /**
     * aad, used for verifying the content
     */
    private byte[] aad;

    /**
     * This method creates an instance of the AES GCM decryptor
     * @param key, the key for this decryptor
     * @param iv, the initialization vector
     * @param aad, the aad, used for verifying the content
     * @param hashType, the type of hash, used by this decryptor
     * @throws Exception, is thrown, when something bad happens
     */
    public AesGcmDecryptor(String key, byte[] iv, byte[] aad, String hashType) throws Exception {
        super("NoPadding", key, hashType);
        this.iv = new GCMParameterSpec(96, iv);
        this.aad = aad;
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        //Getting an instance from the bouncy castle provider
        Cipher cipher = Cipher.getInstance("AES/GCM/" + getPadding(), "BC");
        //initializing the cipher, by setting the decryption mode, passing the key and the GCMParameterSpec
        cipher.init(Cipher.DECRYPT_MODE, getKey(), iv);
        //updating the aad
        cipher.updateAAD(aad);
        //getting the size of the output array
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        //decrypting the first blocks
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //decrypting the last blocks (hash)
        cipher.doFinal(output, ctLength);
        return removeHash(output);
    }
}

