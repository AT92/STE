package app.logic.AES;


import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

public final class AESDecrypto {
    private final SecretKey key;

    public AESDecrypto(String key) {
        byte[] keyB = Base64.getDecoder().decode(key);
        assert keyB.length == 16 || keyB.length == 24 || keyB.length == 32;
        this.key = new SecretKeySpec(keyB, "AES");
    }

    public String decryptAES(String content) throws NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, InvalidKeyException, ShortBufferException,
            BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        byte[] input = Base64.getDecoder().decode(content);
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] output = new byte[input.length];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        cipher.doFinal(output, ctLength);
        return new String(output);
    }
}
