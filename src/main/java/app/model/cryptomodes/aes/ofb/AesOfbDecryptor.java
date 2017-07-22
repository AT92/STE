package app.model.cryptomodes.aes.ofb;


import app.model.cryptomodes.aes.cbc.AesCbcDecryptor;

import javax.crypto.Cipher;

public final class AesOfbDecryptor extends AesCbcDecryptor {
    public AesOfbDecryptor(String key, byte[] IV ) {
        super("NoPadding", key, IV);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/OFB/" + getPadding(), "BC");
        cipher.init(Cipher.DECRYPT_MODE, getKey(), getIvParamSpec());
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        cipher.doFinal(output, ctLength);
        return output;
    }
}
