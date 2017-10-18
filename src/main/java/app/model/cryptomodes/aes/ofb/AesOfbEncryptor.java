package app.model.cryptomodes.aes.ofb;


import app.model.cryptomodes.aes.cbc.AesCbcEncryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

/**
 * This class creates an encryptor, which encrypts information
 * with AES in OFB mode.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final  class AesOfbEncryptor extends AesCbcEncryptor {

    /**
     * This method creates an instance of the AES OFB Encryptor
     * @param keySize, the keysize for this encryptor
     * @param hashType, the type of hash, this encryptor uses
     * @throws Exception, is thrown when something bad happens
     */
    public AesOfbEncryptor(int keySize, String hashType) throws Exception {
        super(keySize, "NoPadding", hashType);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        //Getting the encryptor form the bouncy castle provider
        Cipher cipher = Cipher.getInstance("AES/OFB/" + getPadding(), "BC");
        //Creating a random initialization vector
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte ivB[] =  new byte[16];
        random.nextBytes(ivB);
        //setting the parameterSpec
        setIVParamSpec(new IvParameterSpec(ivB));
        //initializing the cipher by setting the encryption mode, the key and the iv
        cipher.init(Cipher.ENCRYPT_MODE, getKey(), getIvParamSpec());
        //creating the output array with the correct size
        byte[] output = getBytesForCipher(cipher, input);
        //encrypting the first blocks
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //updating and appending the hash
        updateHash(input);
        cipher.doFinal(getDigest(), 0, getDigest().length, output, ctLength);
        return output;
    }
}
