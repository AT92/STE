package app.model.cryptomodes.des.ofb;


import app.model.cryptomodes.des.cbc.DesCbcDecryptor;

import javax.crypto.Cipher;

/**
 * This class stand for a DES decryptor, which works
 * in OFB mode.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class DesOfbDecryptor extends DesCbcDecryptor {
    /**
     * This is a constructor for the DES OFB decryptor
     * @param key, the key this decryptor uses.
     * @param IV, the initialization vector for this decryptor.
     * @param hashType, the type of hash
     * @throws Exception, is thrown, when something bad happens
     */
    public DesOfbDecryptor(String key, byte[] IV, String hashType) throws Exception {
        super("NoPadding", key, IV, hashType);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        //Getting an instance of the cipher from the bouncy castle provider
        Cipher cipher = Cipher.getInstance("DES/OFB/" + getPadding(), "BC");
        //initialization of the cipher by setting the decryption mode, setting the key and the iv
        cipher.init(Cipher.DECRYPT_MODE, getKey(), getIVParamSpec());
        //getting an array for the output with the correct size
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        //decrypting the content
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //decrypting the hash
        cipher.doFinal(output, ctLength);
        return removeHash(output);
    }
}
