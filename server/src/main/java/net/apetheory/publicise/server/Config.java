package net.apetheory.publicise.server;

import com.google.gson.Gson;
import net.apetheory.publicise.server.model.DatabaseConfigModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Server config used to configure
 * the server on start
 */
public class Config {
    private static Config instance;
    private DatabaseConfigModel database;

    private static final String DEFAULT_CONFIG_FILE_PATH = "./config.json";
    private static final int DATABASE_DEFAULT_PORT = 27017;
    private static final String DATABASE_DEFAULT_HOST = "localhost";

    private Config() {}

    /**
     * Loads the config as singleton instance
     *
     * @param configFilePath The file path where the config is located
     * @return The loaded config
     */
    public synchronized static Config load(String configFilePath) {
        configFilePath = configFilePath != null ? configFilePath : DEFAULT_CONFIG_FILE_PATH;

        if(instance == null) {
            String json = readConfigFromFS(configFilePath);

            if(json != null) {
                //TODO log custom config is loaded
                //TODO handle exceptions
                instance = new Gson().fromJson(json, Config.class);
            } else {
                //TODO log default config is loaded
                instance = new Config();
            }
        }

        return instance;
    }

    /**
     * Gets the port of the database
     * @return The port
     */
    public int getDatabasePort() {
        return database != null ? database.getPort() : DATABASE_DEFAULT_PORT;
    }

    /**
     * Gets the host of the database
     * @return the host
     */
    public String getDatabaseHost() {
        return database != null ? database.getHost() : DATABASE_DEFAULT_HOST;
    }

    /**
     * Reads the config from file system
     *
     * @param path The file path where the config is located
     * @return The file content of the config
     */
    private static String readConfigFromFS(String path) {
        String result, line;

        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder builder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            result = builder.toString();

        } catch (IOException e) {
            //TODO Log error
            result = null;
        }

        return result;
    }


}
