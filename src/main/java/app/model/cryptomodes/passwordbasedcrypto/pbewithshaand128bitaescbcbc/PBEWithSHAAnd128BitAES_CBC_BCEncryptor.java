package app.model.cryptomodes.passwordbasedcrypto.pbewithshaand128bitaescbcbc;

import app.model.Configuration;
import app.model.Encryptor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
/**
 * This class creates a PBEWithSHAAnd128BitAES_CBC_BCEncryptor Decryptor
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public class PBEWithSHAAnd128BitAES_CBC_BCEncryptor extends Encryptor {
    /**
     * This method creates a new PBEWithSHAAnd128BitAES_CBC_BCEncryptor.
     * @param hashType, the type of hash this encryptor is using.
     * @param key, the key, this encryptor is using.
     * @throws Exception, is thrown, when something bad happens.
     */
    public PBEWithSHAAnd128BitAES_CBC_BCEncryptor(String hashType, SecretKey key) throws Exception {
        super(hashType);
        setKey(key);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        //Getting the cipher instance from the bouncy castle provider.
        Cipher cipher = Cipher.getInstance("PBEWithSHAAnd128BitAES-CBC-BC", "BC");
        //initializing the cipher by passing the key and setting it to the encryption mode.
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        //creating the output array with the correct size
        byte[] output = getBytesForCipher(cipher, input);
        //encrypting the first blocks
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //encrypting and adding hash
        updateHash(input);
        cipher.doFinal(getDigest(), 0, getDigest().length, output, ctLength);
        return output;
    }

    @Override
    public Configuration updateConfiguration(Configuration config) {
        return config;
    }
}
