package app.model.cryptomodes.rsa;


import app.model.Configuration;
import app.model.Encryptor;

import javax.crypto.Cipher;
import java.security.Key;

/**
 * This class stand for the RSA encryptor.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class RSAEncryptor extends Encryptor {
    /**
     * This method creates an instance of the RSA encryptor.
     * @param hashType, the type of hashing this encryptor uses.
     * @param pubKey, the public key for the encryption.
     * @throws Exception, is thrown, when something bad happens.
     */
    public RSAEncryptor(String hashType, String pubKey) throws Exception {
        super(hashType);
        Key key = RSAKeyManager.publicKeyFromString(pubKey);
        setKey(key);
    }

    @Override
    protected byte[] encrypt(byte[] input) throws Exception {
        //Getting a cipher instance from the bouncy castle provider
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        //initializing it, by setting the encryption mode and setting the public key
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        //getting an array for the output in its size
        byte[] output = getBytesForCipher(cipher, input);
        //encrypting the first blocks
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //setting hash to the end
        updateHash(input);
        cipher.doFinal(getDigest(), 0, getDigest().length, output, ctLength);
        return output;
    }

    @Override
    public Configuration updateConfiguration(Configuration config) {
        config.removeSetting("PublicKey");
        return config;
    }
}
