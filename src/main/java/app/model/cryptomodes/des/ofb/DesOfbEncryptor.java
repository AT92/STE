package app.model.cryptomodes.des.ofb;


import app.model.cryptomodes.des.cbc.DesCbcEncryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

public final class DesOfbEncryptor extends DesCbcEncryptor {
    public DesOfbEncryptor() throws Exception {
        super("NoPadding");
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/OFB/" + getPadding(), "BC");
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte ivB[] =  new byte[8];
        random.nextBytes(ivB);
        setIVParamSpec(new IvParameterSpec(ivB));
        cipher.init(Cipher.ENCRYPT_MODE, getKey(), getIVParamSpec());
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        cipher.doFinal(output, ctLength);
        return output;
    }

}
