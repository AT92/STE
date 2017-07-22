package app.model.cryptomodes.aes.cts;


import app.model.cryptomodes.aes.cbc.AesCbcDecryptor;

import javax.crypto.Cipher;

public final class AesCtsDecryptor extends AesCbcDecryptor {

    public AesCtsDecryptor(String padding, String key, byte[] IV) {
        super(padding, key, IV);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CTS/" + getPadding(), "BC");
        cipher.init(Cipher.DECRYPT_MODE, getKey(), getIvParamSpec());
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        ctLength += cipher.doFinal(output, ctLength);
        byte[] plaintext = new byte[ctLength - getIvParamSpec().getIV().length];
        System.arraycopy(output, getIvParamSpec().getIV().length, plaintext, 0, plaintext.length);
        return plaintext;
    }
}
