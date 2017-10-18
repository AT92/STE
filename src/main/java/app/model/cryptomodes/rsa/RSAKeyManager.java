package app.model.cryptomodes.rsa;


import app.model.Configuration;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * This class stand for RSA key manager. It creates a pair of RSA keys and
 * converts an array of bytes key to string and backwards.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class RSAKeyManager {

    /**
     * This method creates a pair of RSA keys.
     * @param keysize, the size, the RSA keys should have.
     * @return a pair of RSA keys.
     * @throws Exception, thrown, when something bad happens.
     */
    public static Configuration generateKeyPair(int keysize) throws Exception {
        assert keysize == 1024 || keysize == 2048;
        //Getting a keygenerator instance from the bouncy castle provider
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
        //initializing the key generator, by passing the Secure Random, and saying, that rsa keys should be created
        keyGen.initialize(new RSAKeyGenParameterSpec(keysize, RSAKeyGenParameterSpec.F4), new SecureRandom());
        KeyPair pair = keyGen.genKeyPair();
        Configuration conf = new Configuration();
        //saving both keys in a configuration
        conf.addSetting("PublicKey", keyToString(pair.getPublic()));
        conf.addSetting("PrivateKey", keyToString(pair.getPrivate()));
        return conf;
    }

    /**
     * Returns a string represantation of a key.
     * @param key, the key, which should be converted to a string.
     * @return the string reprasentation of a key.
     */
    private static String keyToString(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * This converts a public key in string represantation to a Key object.
     * @param key, the public key as a string.
     * @return the public key as a key object.
     * @throws Exception, is thrown, when something bad happens.
     */
    static Key publicKeyFromString(String key) throws Exception {
        return KeyFactory.getInstance("RSA", "BC")
                .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(key)));
    }

    /**
     * This converts a private key in string represantation to a Key object.
     * @param key, the private key as a string.
     * @return the private key as a key object.
     * @throws Exception, is thrown, when something bad happens.
     */
    static Key privateKeyFromString(String key) throws Exception {
        return KeyFactory.getInstance("RSA", "BC")
                .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key)));
    }
}
