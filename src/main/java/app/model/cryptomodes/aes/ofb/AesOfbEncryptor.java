package app.model.cryptomodes.aes.ofb;


import app.model.cryptomodes.aes.cbc.AesCbcEncryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

public final  class AesOfbEncryptor extends AesCbcEncryptor {

    public AesOfbEncryptor(int keySize) throws Exception {
        super(keySize, "NoPadding");
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/OFB/" + getPadding(), "BC");
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte ivB[] =  new byte[16];
        random.nextBytes(ivB);
        setIVParamSpec(new IvParameterSpec(ivB));
        cipher.init(Cipher.ENCRYPT_MODE, getKey(), getIvParamSpec());
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        cipher.doFinal(output, ctLength);
        return output;
    }
}
