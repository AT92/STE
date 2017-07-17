package app.logic;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

public abstract class Decryptor {
    private SecretKeySpec key;
    private String padding;

    public Decryptor(String padding) {
        this.padding = padding;
    }

    public abstract byte[] decrypt(byte[] input) throws Exception;

    public String decryptAsString(String content) throws Exception {
        return new String(decrypt(Base64.getDecoder().decode(content)), StandardCharsets.US_ASCII)
                .replaceAll("\\u0000+$", "");
    }

    public void setKey(SecretKeySpec key) {
        this.key = key;
    }

    public SecretKeySpec getKey() {
        return key;
    }

    public String getPadding() {
        return padding;
    }
}