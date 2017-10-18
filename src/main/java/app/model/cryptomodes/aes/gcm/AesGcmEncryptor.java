package app.model.cryptomodes.aes.gcm;


import app.model.Configuration;
import app.model.cryptomodes.aes.AesEncryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * This class creates an encryptor, which encrypts information
 * with AES in GCM mode.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class AesGcmEncryptor extends AesEncryptor {
    /**
     * The ParameterSpec used by the GCM
     */
    private GCMParameterSpec iv;
    //The aad used by GCM, for verifying the content
    private final byte[] aad = new byte[12];

    /**
     * This method creates an instance of the GCM encryptor.
     * @param keysize, the keysize for the AES cipher
     * @param hashType, the hashtype this encryptor uses.
     * @throws Exception, is thrown, when something bad happened
     */
    public AesGcmEncryptor(int keysize, String hashType) throws Exception {
        super(keysize, "NoPadding", hashType);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        //Getting the cipher from the factory
        Cipher cipher = Cipher.getInstance("AES/GCM/" + getPadding(), "BC");
        //Creating the random nonce and the aad
        byte[] nonce = new byte[16];
        SecureRandom.getInstanceStrong().nextBytes(nonce);
        SecureRandom.getInstanceStrong().nextBytes(aad);
        //initializing the ParameterSpec
        iv = new GCMParameterSpec(96, nonce);
        //Getting the cipher from the factory
        cipher.init(Cipher.ENCRYPT_MODE, getKey(), iv);
        //Updating the aad
        cipher.updateAAD(aad);
        //creating the output array with the needed size
        byte[] output = getBytesForCipher(cipher, input);
        //encrypting the content
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //updating the hash
        updateHash(input);
        //encrypting the hash information
        cipher.doFinal(getDigest(), 0, getDigest().length, output, ctLength);
        return output;
    }

    @Override
    public Configuration updateConfiguration(Configuration config) {
        config.addSetting("IV", Base64.getEncoder().encodeToString(iv.getIV()));
        config.addSetting("Key", getKeyAsString());
        config.addSetting("AAD", Base64.getEncoder().encodeToString(aad));
        config.addSetting("HashLength", String.valueOf(getDigest().length));
        return config;
    }
}
