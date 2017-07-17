package app.logic.AES.CBC;


import app.logic.AES.AESEncryptor;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

public final class AESCBCEncryptor extends AESEncryptor {
    private IvParameterSpec iv;

    public AESCBCEncryptor(int keySize, String padding)
            throws NoSuchProviderException, NoSuchAlgorithmException {
        super(keySize, padding);
    }

    @Override
    public byte[] encrypt(byte[] input) throws NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, InvalidKeyException, ShortBufferException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/CBC/" + getPadding(), "BC");
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        iv = new IvParameterSpec(cipher.getIV());
        byte[] output = new byte[cipher.getOutputSize(iv.getIV().length + input.length)];
        int ctLength = cipher.update(iv.getIV(), 0, iv.getIV().length, output, 0);
        ctLength += cipher.update(input, 0, input.length, output, 0);
        cipher.doFinal(output, ctLength);
        return output;
    }

    public String getIV() {
        System.out.println(Base64.getEncoder().encodeToString(iv.getIV()).length());
        return Base64.getEncoder().encodeToString(iv.getIV());
    }

    @Override
    public String getKeyAsString() {
        return Base64.getEncoder().encodeToString(getKey().getEncoded()) + getIV();
    }
}
