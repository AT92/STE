package app.model.cryptomodes.passwordbasedcrypto.pbewithmd5anddes;


import app.model.Decryptor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * This class decrypts a PBEWithMD5AndDES encrypted text.
 * This is a decryptor.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class PBEWithMD5AndDESDecryptor extends Decryptor {
    /**
     * This is a constructor for the PBEWithMD5AndDES-Decryptor.
     * @param hash, the hash method, this text has.
     * @param key, the key, which this decryptor is using.
     * @throws Exception, is thrown, when something bad happens.
     */
    public PBEWithMD5AndDESDecryptor(String hash, SecretKey key) throws Exception {
        super(hash);
        setKey(key);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        //Getting an instance of the cipher form the bouncy castle provider
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES", "BC");
        //initializing the cipher by setting the decryption mode and the key.
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        //creating an outputarray with the correct size
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        //decrypting the first blocks
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //decrypting the last block and the hash
        ctLength += cipher.doFinal(output, ctLength);
        return removeHash(output, ctLength);
    }
}
