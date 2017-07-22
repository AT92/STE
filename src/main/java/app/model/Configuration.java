package app.model;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public final class Configuration {
    private HashMap<String, String> config;

    @SuppressWarnings("unchecked")
    public Configuration(String config) throws IOException {
        this.config = new ObjectMapper().readValue(config, HashMap.class);
    }

    public Configuration() {
        this.config = new HashMap<>();
    }

    public void addSetting(String setting, String value) {
        config.put(setting, value);
    }

    public String getSetting(String setting) throws IllegalStateException {
        if (config.containsKey(setting)) {
            return config.get(setting);
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean settingExist(String setting) {
        return config.containsKey(setting);
    }

    @Override
    public String toString() {
        return new JSONObject(config).toString();
    }
}
