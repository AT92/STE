package app.model;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * This class is the container for the configuration, which is by the encryptors
 * and the decryptors.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class Configuration {
    /**
     * Hahsmap, which stores the configuration. Key is the name of the configuration, values is its value.
     */
    private HashMap<String, String> config;

    /**
     * This is the constructor for the configuration file.
     *
     * @param config, the configuration as a String in JSON format.
     * @throws IOException, is thrown, when JSOn can not be converted to hashmap
     */
    @SuppressWarnings("unchecked")
    public Configuration(String config) throws IOException {
        this.config = new ObjectMapper().readValue(config, HashMap.class);
    }

    /**
     * This is the constructor for the empty configuration file.
     */
    public Configuration() {
        this.config = new HashMap<String, String>();
    }

    /**
     * This method adds a new setting to the configuration and overrides the old one, if it exist.
     * @param setting, the new setting
     * @param value, the value of the new setting.
     */
    public void addSetting(String setting, String value) {
        config.put(setting, value);
    }

    /**
     * This method removes a setting from the configuration.
     * @param setting, the setting which should be removed.
     */
    public void removeSetting(String setting) {
        assert settingExist(setting);
        config.remove(setting);
    }

    /**
     * This method gets a value of the passed setting.
     * @param setting, which values is asked.
     * @return the value of the passsed setting.
     * @throws IllegalStateException, is thrown, when the setting does not exist.
     */
    public String getSetting(String setting) throws IllegalStateException {
        if (config.containsKey(setting)) {
            return config.get(setting);
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * This method checks if the setting exist in the configuration.
     * @param setting, the setting which should be checked.
     * @return true, if the setting exist, false otherwise.
     */
    public boolean settingExist(String setting) {
        return config.containsKey(setting);
    }

    @Override
    public String toString() {
        return new JSONObject(config).toString();
    }
}
