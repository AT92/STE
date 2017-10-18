package app.model.cryptomodes.des.ecb;


import app.model.cryptomodes.des.DesDecryptor;

import javax.crypto.Cipher;

/**
 * This class stand for a DES decryptor, which works
 * in ECB mode.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class DesEcbDecryptor extends DesDecryptor {
    /**
     * This is a constructor for the DES ECB decryptor
     * @param padding, the padding this decryptor uses.
     * @param key, the key this decryptor uses.
     * @param hashType, the type of hash
     * @throws Exception, is thrown, when something bad happens
     */
    public DesEcbDecryptor(String padding, String key, String hashType) throws Exception {
        super(padding, key, hashType);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        //Getting an instance of the ECB decryptor from the bouncy castle provider
        Cipher cipher = Cipher.getInstance("DES/ECB/" + getPadding(), "BC");
        //setting the cipher to the decryption mode and passing the key to it
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        //creating the array for the output
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        //decrypting the first blocks
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //decrypting the last block
        ctLength += cipher.doFinal(output, ctLength);
        //removing the hash and the padding
        return removeHash(output, ctLength);
    }
}
