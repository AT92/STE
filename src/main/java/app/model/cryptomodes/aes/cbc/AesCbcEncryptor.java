package app.model.cryptomodes.aes.cbc;


import app.model.Configuration;
import app.model.cryptomodes.aes.AesEncryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

/**
 * This class creates an encryptor, which encrypts information
 * with AES in CBC mode.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public class AesCbcEncryptor extends AesEncryptor {
    /**
     * The initialization vector, which is required by the cbc mode.
     */
    private IvParameterSpec iv;

    /**
     * This is the constuctor for the AesCbcEncryptor.
     * @param keysize, the keysize for the AES cipher
     * @param padding, the padding this encryptor uses.
     * @param hashType, the hashtype this ancryptor uses.
     * @throws Exception, is thrown, when something bad happened
     */
    public AesCbcEncryptor(int keysize, String padding, String hashType) throws Exception {
        super(keysize, padding, hashType);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        //Getting the cipher from the factory
        Cipher cipher = Cipher.getInstance("AES/CBC/" + getPadding(), "BC");
        //initializayion of the cipher, by setting an encryption mode and passing the key
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        //the cipher creates the iv for this encryption and this will be stored
        setIVParamSpec(new IvParameterSpec(cipher.getIV()));
        //creating an array for the output, which length = iv.length, input.length + the length required for hashing
        byte[] output = new byte[cipher.getOutputSize(getIvParamSpec().getIV().length + input.length + getDigest().length)];
        //encrypting the first block with the iv
        int ctLength = cipher.update(getIvParamSpec().getIV(), 0, getIvParamSpec().getIV().length, output, 0);
        //encrypting the rest blocks
        ctLength += cipher.update(input, 0, input.length, output, ctLength);
        //updating the hash
        updateHash(input);
        //encrypting the last block and adding some padding, if required
        cipher.doFinal(getDigest(), 0, getDigest().length, output, ctLength);
        return output;
    }

    /**
     * This method creates the represantation of the iv as a string.
     * @return the iv as a string.
     */
    private String getIVAsString() {
        return Base64.getEncoder().encodeToString(iv.getIV());
    }

    /**
     * This is a setter method for the initialization vector.
     * @param iv, the initialization vector for this cipher.
     */
    protected void setIVParamSpec(IvParameterSpec iv) {
        this.iv = iv;
    }

    /**
     * Getter method for the initialization vector.
     * @return iv, the initialization vector.
     */
    protected IvParameterSpec getIvParamSpec() {
        return iv;
    }

    @Override
    public Configuration updateConfiguration(final Configuration config) {
        //storing the iv in the configuration
        config.addSetting("IV", getIVAsString());
        //storing the key in the configuration
        config.addSetting("Key", getKeyAsString());
        return config;
    }
}
