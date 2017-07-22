package app.model.cryptomodes.aes.ecb;


import app.model.cryptomodes.aes.AesDecryptor;


import javax.crypto.*;

public final class AesEcbDecryptor extends AesDecryptor {
    public AesEcbDecryptor(String padding, String key) {
        super(padding, key);
    }

    public byte[] decrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/" + getPadding(), "BC");
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        cipher.doFinal(output, ctLength);
        return output;
    }
}
