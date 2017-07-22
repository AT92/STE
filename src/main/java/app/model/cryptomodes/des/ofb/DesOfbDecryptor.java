package app.model.cryptomodes.des.ofb;


import app.model.cryptomodes.des.cbc.DesCbcDecryptor;

import javax.crypto.Cipher;

public final class DesOfbDecryptor extends DesCbcDecryptor {
    public DesOfbDecryptor(String key, byte[] IV) {
        super("NoPadding", key, IV);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/OFB/" + getPadding(), "BC");
        cipher.init(Cipher.DECRYPT_MODE, getKey(), getIVParamSpec());
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        cipher.doFinal(output, ctLength);
        return output;
    }
}
