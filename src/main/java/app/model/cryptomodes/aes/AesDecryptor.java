package app.model.cryptomodes.aes;


import app.model.Decryptor;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public abstract class AesDecryptor extends Decryptor {

    public AesDecryptor(String padding, String key) {
        super(padding);
        byte[] keyB = Base64.getDecoder().decode(key);
        assert keyB.length == 16 || keyB.length == 24 || keyB.length == 32;
        setKey(new SecretKeySpec(keyB, "AES"));
    }

    @Override
    public abstract byte[] decrypt(byte[] input) throws Exception;
}
