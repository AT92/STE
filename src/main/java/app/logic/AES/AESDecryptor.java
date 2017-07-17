package app.logic.AES;


import app.logic.AES.CBC.AESCBCDecryptor;
import app.logic.Decryptor;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public abstract class AESDecryptor extends Decryptor {

    public AESDecryptor(String padding, String key) {
        super(padding);
        if (this.getClass() != AESCBCDecryptor.class) {
            byte[] keyB = Base64.getDecoder().decode(key);
            setKey(new SecretKeySpec(keyB, "AES"));
        }
    }

    @Override
    public abstract byte[] decrypt(byte[] input) throws Exception;
}
