package app.logic.AES;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;


public final class AESEncrypto {
    private final SecretKey key;

    public AESEncrypto(int keyLength) throws NoSuchProviderException, NoSuchAlgorithmException {
        if (keyLength != 128 && keyLength != 192 && keyLength != 256) {
            throw new IllegalStateException("Wrong keysize");
        }
        KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC");
        keyGen.init(keyLength);
        key = keyGen.generateKey();
    }

    public String encryptAES(String content) throws NoSuchAlgorithmException, NoSuchProviderException,
                                                    NoSuchPaddingException, InvalidKeyException, ShortBufferException,
                                                    BadPaddingException, IllegalBlockSizeException {
        byte[] input = content.getBytes(StandardCharsets.US_ASCII);
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] output = new byte[input.length];
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        cipher.doFinal(output, ctLength);
        return Base64.getEncoder().encodeToString(output);
    }
    
    public String getKey() {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
