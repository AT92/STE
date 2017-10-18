package app.model.cryptomodes.des.ofb;


import app.model.cryptomodes.des.cbc.DesCbcEncryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
/**
 * This class stand for a DES encryptor, which works
 * in OFB mode.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class DesOfbEncryptor extends DesCbcEncryptor {
    /**
     * This method creates a DES ECB encryptor
     * @param hashType, the type of hash this encryptor uses.
     * @throws Exception, is thrown, when something bad happens.
     */
    public DesOfbEncryptor(String hashType) throws Exception {
        super("NoPadding", hashType);
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        //Getting an instance of the cipher in the ofb mode from the bouncy castle provider
        Cipher cipher = Cipher.getInstance("DES/OFB/" + getPadding(), "BC");
        //creating a random initialization vector
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte ivB[] =  new byte[8];
        random.nextBytes(ivB);
        setIVParamSpec(new IvParameterSpec(ivB));
        //initializing the cipher by setting the encryption mode, the key and the ivSpec
        cipher.init(Cipher.ENCRYPT_MODE, getKey(), getIvParamSpec());
        //creating the output array with the right size
        byte[] output = getBytesForCipher(cipher, input);
        //encrypting the content
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //updating the hash
        updateHash(input);
        //encrypting the hash
        cipher.doFinal(getDigest(), 0, getDigest().length, output, ctLength);
        return output;
    }

}
