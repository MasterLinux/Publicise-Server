package net.apetheory.publicise.server;

import flexjson.JSONDeserializer;
import net.apetheory.publicise.server.data.ResourceFileReader;
import net.apetheory.publicise.server.data.utility.StringUtils;
import net.apetheory.publicise.server.model.ConfigModel;

/**
 * Server config used to configure
 * the server on start
 */
public class Config {
    private static Config instance;
    private final ConfigModel config;

    private static final String DEFAULT_CONFIG_FILE_PATH = "config.json";
    private static final String DATABASE_DEFAULT_HOST = "localhost";
    private static final int DATABASE_DEFAULT_PORT = 27017;

    private Config() {
        config = new ConfigModel();
    }

    public Config(ConfigModel config) {
        this.config = config;
    }

    /**
     * Loads the config as singleton instance
     *
     * @param configFilePath The file path where the config is located
     * @return The loaded config
     */
    public synchronized static Config load(String configFilePath) throws MissingConfigException {
        configFilePath = configFilePath != null ? configFilePath : DEFAULT_CONFIG_FILE_PATH;

        if (instance == null) {
            String json = new ResourceFileReader().readFile(configFilePath);

            if (json != null) {
                //TODO use new ResourceFileReader("documentation.html").readFile() to load config from resources
                //TODO log custom config is loaded
                //TODO handle exceptions
                ConfigModel config = new JSONDeserializer<ConfigModel>().deserialize(json, ConfigModel.class);
                instance = new Config(config);
            } else {
                throw new MissingConfigException();
            }
        }

        return instance;
    }

    /**
     * Loads the default config as singleton instance
     *
     * @return The default config
     */
    public synchronized static Config load() throws MissingConfigException {
        return load(null);
    }

    /**
     * Gets the port of the database
     *
     * @return The port
     */
    public int getDatabasePort() {
        int port = config.getDatabase() != null ? config.getDatabase().getPort() : 0;

        if (port == 0) {
            port = DATABASE_DEFAULT_PORT;
        }

        return port;
    }

    /**
     * Gets the host of the database
     *
     * @return the host
     */
    public String getDatabaseHost() {
        String host = config.getDatabase() != null ? config.getDatabase().getHost() : null;

        if (StringUtils.isNullOrEmpty(host)) {
            host = DATABASE_DEFAULT_HOST;
        }

        return host;
    }

    public String getDatabaseName() throws MissingNameException {
        String name = config.getDatabase() != null ? config.getDatabase().getName() : null;

        if (StringUtils.isNullOrEmpty(name)) {
            throw new MissingNameException();
        }

        return name;
    }

    public static class MissingNameException extends Exception {

        public MissingNameException() {
            super("Missing database name");
        }
    }

    public static class MissingConfigException extends Exception {

        public MissingConfigException() {
            super("Config could not be loaded");
        }
    }
}
