package app.model.cryptomodes.passwordbasedcrypto.pbewithshaand128bitaescbcbc;

import app.model.Decryptor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
/**
 * This class creates a PBEWithSHAAnd128BitAES_CBC_BCDecryptor
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class PBEWithSHAAnd128BitAES_CBC_BCDecryptor extends Decryptor {
    /**
     * This method creates a new PBEWithSHAAnd128BitAES_CBC_BCDecryptor.
     * @param hashType, the type of hash this decryptor is using.
     * @param key, the key, this decryptor is using.
     * @throws Exception, is thrown, when something bad happens.
     */
    public PBEWithSHAAnd128BitAES_CBC_BCDecryptor(String hashType, SecretKey key) throws Exception {
        super(hashType);
        setKey(key);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        //Getting the cipher instance
        Cipher cipher = Cipher.getInstance("PBEWithSHAAnd128BitAES-CBC-BC", "BC");
        //initializing the cipher by setting the decryption mode and passing the key.
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        //creating the output array with the correct size
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        //decrypting the first blocks
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //decrypting the last block
        ctLength += cipher.doFinal(output, ctLength);
        //removing the ahs and the padding
        return removeHash(output, ctLength);
    }
}
