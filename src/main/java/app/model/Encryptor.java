package app.model;


import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public abstract class Encryptor {
    private SecretKey key;
    private String padding;

    public Encryptor(String padding) {
        this.padding = padding;
    }

    public byte[] encrypt(String content) throws Exception {
        return encrypt(content.getBytes(StandardCharsets.US_ASCII));
    }

    public String encryptAsString(String content) throws Exception {
        return Base64.getEncoder().encodeToString(encrypt(content));
    }

    public abstract byte[] encrypt(byte[] content) throws Exception;

    protected String getKeyAsString() {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public String getPadding() {
        return padding;
    }

    public abstract Configuration updateConfiguration(Configuration config);
}
