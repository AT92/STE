package app.model.cryptomodes.aes.ecb;

import app.model.Configuration;
import app.model.cryptomodes.aes.AesEncryptor;

import javax.crypto.Cipher;

/**
 * This class creates an encryptor, which encrypts information
 * with AES in ECB mode.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class AesEcbEncryptor extends AesEncryptor {
    /**
     * This is the constuctor for the AesEcbEncryptor.
     * @param keysize, the keysize for the AES cipher
     * @param padding, the padding this encryptor uses.
     * @param hashType, the hashtype this encryptor uses.
     * @throws Exception, is thrown, when something bad happened
     */
    public AesEcbEncryptor(int keysize, String padding, String hashType) throws Exception {
        super(keysize, padding, hashType);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        //Getting the cipher from the factory
        Cipher cipher = Cipher.getInstance("AES/ECB/" + getPadding(), "BC");
        //initialization of the cipher, by setting an encryption mode and passing the key
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        //creating an array with the size of output
        byte[] output = getBytesForCipher(cipher, input);
        //encrypting first blocks
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //updating the hash
        updateHash(input);
        //encrypting the last
        cipher.doFinal(getDigest(), 0, getDigest().length, output, ctLength);
        return output;
    }

    @Override
    public Configuration updateConfiguration(Configuration config) {
        config.addSetting("Key", getKeyAsString());
        return config;
    }
}
