package app.model.cryptomodes.aes.cts;


import app.model.cryptomodes.aes.cbc.AesCbcEncryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

/**
 * This class creates an encryptor, which encrypts information
 * with AES in CTS mode.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class AesCtsEncryptor extends AesCbcEncryptor {

    /**
     * This is the constuctor for the AesCbcEncryptor.
     * @param keysize, the keysize for the AES cipher
     * @param padding, the padding this encryptor uses.
     * @param hashType, the hashtype this ancryptor uses.
     * @throws Exception, is thrown, when something bad happened
     */
    public AesCtsEncryptor(int keysize, String padding, String hashType) throws Exception {
        super(keysize, padding, hashType);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        //Getting the cipher from the factory
        Cipher cipher = Cipher.getInstance("AES/CTS/" + getPadding(), "BC");
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
}
