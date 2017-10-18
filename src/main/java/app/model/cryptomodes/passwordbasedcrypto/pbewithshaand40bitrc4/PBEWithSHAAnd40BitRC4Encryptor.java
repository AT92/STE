package app.model.cryptomodes.passwordbasedcrypto.pbewithshaand40bitrc4;


import app.model.Configuration;
import app.model.Encryptor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * This class creates an PBEWithSHAAnd40BitRC4Encryptor.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class PBEWithSHAAnd40BitRC4Encryptor extends Encryptor {

    /**
     * This method creates a new PBEWithSHAAnd40BitRC4Encryptor.
     * @param hashType, the type of hash this encryptor is using.
     * @param key, the key, this encryptor is using.
     * @throws Exception, is thrown, when something bad happens.
     */
    public PBEWithSHAAnd40BitRC4Encryptor(String hashType, SecretKey key) throws Exception {
        super(hashType);
        setKey(key);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        //Getting the cipher instance from the bouncy castle provider
        Cipher cipher = Cipher.getInstance("PBEWithSHAAnd40BitRC4", "BC");
        //Setting the encryption mode and passing the key.
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        //Creating an output array with the correct size.
        byte[] output = getBytesForCipher(cipher, input);
        //encrypting the content
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //adding the hash
        updateHash(input);
        cipher.doFinal(getDigest(), 0, getDigest().length, output, ctLength);
        return output;
    }

    @Override
    public Configuration updateConfiguration(Configuration config) {
        return config;
    }
}
