package app.model.cryptomodes.passwordbasedcrypto.pbewithmd5anddes;


import app.model.Configuration;
import app.model.Encryptor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * This class creates a PBEWithMD5AndDESEncryptor.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class PBEWithMD5AndDESEncryptor extends Encryptor {

    /**
     * This is a constructor for the PBEWithMD5AndDES Encryptor.
     * @param hash, the type of hash this encryptor is using.
     * @param key, the key this encryptor is using.
     * @throws Exception, is thrown, when something bad happened.
     */
    public PBEWithMD5AndDESEncryptor(String hash, SecretKey key) throws Exception {
        super(hash);
        setKey(key);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        //Getting a cipher instance from the bouncy castle provider
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES", "BC");
        //initializing the cipher by setting the encryption mode and passing the key
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        //creating an array with the correct size of output
        byte[] output = getBytesForCipher(cipher, input);
        //encrypting the first blocks
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        updateHash(input);
        //encrypting the hash
        cipher.doFinal(getDigest(), 0, getDigest().length, output, ctLength);
        return output;
    }

    @Override
    public Configuration updateConfiguration(Configuration config) {
        return config;
    }
}
