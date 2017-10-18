package app.model.cryptomodes.passwordbasedcrypto.pbewithshaand40bitrc4;


import app.model.Decryptor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * This class creates a PBEWithSHAand40BitRC4 Decryptor
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class PBEWithSHAAnd40BitRC4Decryptor extends Decryptor {

    /**
     * This method creates a new PBEWithSHAAnd40BitRC4Decryptor.
     * @param hashType, the type of hash this decryptor is using.
     * @param key, the key, this decryptor is using.
     * @throws Exception, is thrown, when something bad happens.
     */
    public PBEWithSHAAnd40BitRC4Decryptor(String hashType, SecretKey key) throws Exception {
        super(hashType);
        setKey(key);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        //Getting an instance of the cipher from the bouncy castle provider.
        Cipher cipher = Cipher.getInstance("PBEWithSHAAnd40BitRC4", "BC");
        //initializing the cipher by setting it to the decryption mode and passing the key.
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        //creating an output array with the correct size.
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        //decrypting the first blocks
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //decrypting the last blocks and the hash
        cipher.doFinal(output, ctLength);
        return removeHash(output);
    }
}
