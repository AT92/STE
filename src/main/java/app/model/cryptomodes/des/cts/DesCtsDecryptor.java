package app.model.cryptomodes.des.cts;

import app.model.cryptomodes.des.cbc.DesCbcDecryptor;

import javax.crypto.Cipher;


public class DesCtsDecryptor extends DesCbcDecryptor {
    public DesCtsDecryptor(String padding, String key, byte[] IV) {
        super(padding, key, IV);
    }

    @Override
    public byte[] decrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CTS/" + getPadding(), "BC");
        cipher.init(Cipher.DECRYPT_MODE, getKey(), iv);
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        ctLength += cipher.doFinal(output, ctLength);
        byte[] plaintext = new byte[ctLength - iv.getIV().length];
        System.arraycopy(output, iv.getIV().length, plaintext, 0, plaintext.length);
        return plaintext;
    }
}
