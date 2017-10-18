package app.model.cryptomodes.des.ecb;


import app.model.Configuration;
import app.model.cryptomodes.des.DesEncryptor;

import javax.crypto.Cipher;
/**
 * This class stand for a DES encryptor, which works
 * in ECB mode.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class DesEcbEncryptor extends DesEncryptor {
    /**
     * This method creates a DES ECB encryptor
     * @param padding, the padding this encryptor uses.
     * @param hashType, the type of hash this encryptor uses.
     * @throws Exception, is thrown, when something bad happens.
     */
    public DesEcbEncryptor(String padding, String hashType) throws Exception {
        super(padding, hashType);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        //Getting the cipher instance from the bouncy castle provider.
        Cipher cipher = Cipher.getInstance("DES/ECB/" + getPadding(), "BC");
        //initializing the cipher by setting the encryption mode and setting the key
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        //creating an output array with the correct size
        byte[] output = getBytesForCipher(cipher, input);
        //encrypting the first blocks
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //updating the hash
        updateHash(input);
        //encrypting the last block and appending the hash
        cipher.doFinal(getDigest(), 0, getDigest().length, output, ctLength);
        return output;
    }

    @Override
    public Configuration updateConfiguration(Configuration config) {
        config.addSetting("Key", getKeyAsString());
        return config;
    }
}
