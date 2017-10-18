package app.model.cryptomodes.passwordbasedcrypto;


import app.model.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * This class creates a key out of the pass, depending on the PBE-mode.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class KeyCreatorPBE {
    /**
     * The configuration, with the PBE mode
     */
    private final Configuration config;
    /**
     * The password, which the user gave in.
     */
    private final String pass;

    /**
     * This method creates a new KeyCreatorPBE object.
     * @param config, the configuration, for the keycreator.
     * @param pass, the password.
     */
    public KeyCreatorPBE(Configuration config, String pass) {
        assert pass.length() > 0;
        this.config = config;
        this.pass = pass;
    }

    /**
     * This method creates a key.
     * @return the created key.
     * @throws Exception, is thrown, when something bad happens.
     */
    public SecretKey createKey() throws Exception {
        //creating a random salt
        byte[] salt = new byte[8];
        SecureRandom.getInstanceStrong().nextBytes(salt);
        //saving the salt into the configuration
        config.addSetting("Salt", Base64.getEncoder().encodeToString(salt));
        //creating the key out of salt and the password and setting iteration amount to 1024
        char[] password = pass.toCharArray();
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password, salt, 1024);
        //generating the key
        return SecretKeyFactory.getInstance(config.getSetting("PbeModus"), "BC").generateSecret(pbeKeySpec);
    }

    /**
     * This method restores a key out of the salt and the password.
     * @return the restored key.
     * @throws Exception, is thrown when something bad happened.
     */
    public SecretKey restoreKey() throws Exception {
        //deocding the salt to byte array
        byte[] salt = Base64.getDecoder().decode(config.getSetting("Salt"));
        char[] password = pass.toCharArray();
        //restoring the key
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password, salt, 1024);
        return SecretKeyFactory.getInstance(config.getSetting("PbeModus"), "BC").generateSecret(pbeKeySpec);
    }

    /**
     * This method returns the updated configuration, with the salt.
     * @return the updated configuration.
     */
    public Configuration getNewConfiguration() {
        assert config.settingExist("Salt");
        return config;
    }
}
