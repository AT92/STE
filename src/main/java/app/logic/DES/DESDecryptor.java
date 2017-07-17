package app.logic.DES;

import app.logic.Decryptor;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

public abstract class DESDecryptor extends Decryptor {

    public DESDecryptor(String padding, String key) {
        super(padding);
        byte[] keyB = Base64.getDecoder().decode(key);
        setKey(new SecretKeySpec(keyB, "DES"));
    }

    @Override
    public abstract byte[] decrypt(byte[] input) throws NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, InvalidKeyException, ShortBufferException,
            BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException;
}
