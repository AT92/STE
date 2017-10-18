package app.model.cryptomodes.des.cbc;


import app.model.Configuration;
import app.model.cryptomodes.des.DesEncryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;
/**
 * This class stand for a DES encryptor, which works
 * in CBC mode.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public class DesCbcEncryptor extends DesEncryptor {
    /**
     * The initialization vector.
     */
    protected IvParameterSpec iv;

    /**
     * This method creates a DES CBC encryptor
     * @param padding, the padding this encryptor uses.
     * @param hashType, the type of hash this encryptor uses.
     * @throws Exception, is thrown, when something bad happens.
     */
    public DesCbcEncryptor(String padding, String hashType) throws Exception {
        super(padding, hashType);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        //Getting an instance of the cipher from the bouncy castle provider.
        Cipher cipher = Cipher.getInstance("DES/CBC/" + getPadding(), "BC");
        //initializing the cipher by setting the key and setting the cipher to encryption mode
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        //generating an initialization vector
        iv = new IvParameterSpec(cipher.getIV());
        //creating an array with the length of output + iv + hash
        byte[] output = new byte[cipher.getOutputSize(getIvParamSpec().getIV().length + input.length + getDigest().length)];
        //encrypting the first block
        int ctLength = cipher.update(getIvParamSpec().getIV(), 0, getIvParamSpec().getIV().length, output, 0);
        //encrypting the rest
        ctLength += cipher.update(input, 0, input.length, output, ctLength);
        //updating the hash and encrypting it finally
        updateHash(input);
        cipher.doFinal(getDigest(), 0, getDigest().length, output, ctLength);
        return output;
    }

    /**
     * This method returns an iv in the string represantation.
     * @return the initialization vector as a string.
     */
    private String getIVAsString() {
        return Base64.getEncoder().encodeToString(iv.getIV());
    }

    /**
     * Setter method for the iv.
     * @param iv, the initialization vector, which should be set.
     */
    protected void setIVParamSpec(IvParameterSpec iv) {
        this.iv = iv;
    }

    /**
     * Getter method for the iv.
     * @return the initialization vector.
     */
    protected IvParameterSpec getIvParamSpec() {
        return iv;
    }

    @Override
    public Configuration updateConfiguration(Configuration config) {
        config.addSetting("IV", getIVAsString());
        config.addSetting("Key", getKeyAsString());
        return config;
    }
}
