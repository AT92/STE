package app.model.cryptomodes.des.cts;


import app.model.cryptomodes.des.cbc.DesCbcEncryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
/**
 * This class stand for a DES encryptor, which works
 * in CTS mode.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class DesCtsEncryptor extends DesCbcEncryptor {

    /**
     * This method creates a DES CTS encryptor
     * @param padding, the padding this encryptor uses.
     * @param hashType, the type of hash this encryptor uses.
     * @throws Exception, is thrown, when something bad happens.
     */
    public DesCtsEncryptor(String padding, String hashType) throws Exception {
        super(padding, hashType);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        //Getting an instance of the cipher from the bouncy castle provider.
        Cipher cipher = Cipher.getInstance("DES/CTS/" + getPadding(), "BC");
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
}
