package app.model.cryptomodes.des;

import app.model.Decryptor;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public abstract class DesDecryptor extends Decryptor {

    public DesDecryptor(String padding, String key) {
        super(padding);
        byte[] keyB = Base64.getDecoder().decode(key);
        setKey(new SecretKeySpec(keyB, "DES"));
    }

    @Override
    public abstract byte[] decrypt(byte[] input) throws Exception;
}
