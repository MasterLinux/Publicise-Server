package net.apetheory.publicise.server.data.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import net.apetheory.publicise.server.Config;
import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.database.exception.ConnectionException;
import net.apetheory.publicise.server.data.database.listener.OnConnectionEstablishedListener;
import org.bson.Document;

import java.util.HashMap;

/**
 * Helper class to establish a connection
 * to the mongoDB database
 */
public class Database {
    private static final HashMap<String, Database> instances = new HashMap<>();
    private static final Object instanceLock = new Object();
    private final MongoClient client;
    private final String name;
    private ResourceSet result;

    /**
     * Initializes the database
     *
     * @param host The host of the database to connect
     * @param port The port of the database to connect
     */
    private Database(String name, String host, int port) {
        client = new MongoClient(host, port);
        this.name = name;
    }

    /**
     * Creates a new database configured by a config
     *
     * @param config The config to configure the database
     * @return The new database instance
     */
    public static Database fromConfig(final Config config) throws Config.MissingNameException {
        final String databaseName = config.getDatabaseName();
        Database database = null;

        if (!instances.containsKey(databaseName)) {
            synchronized (instanceLock) {
                if (!instances.containsKey(databaseName)) {
                    database = new Database(
                            databaseName,
                            config.getDatabaseHost(),
                            config.getDatabasePort()
                    );

                    instances.put(databaseName, database);
                }
            }
        } else {
            database = instances.get(databaseName);
        }

        return database;
    }

    /**
     * Creates a new database using the default config
     *
     * @return The new database instance
     */
    public static Database fromConfig() throws Config.MissingNameException, Config.MissingConfigException {
        Config config = Config.load();
        return fromConfig(config);
    }

    /**
     * Get a specific database collection
     *
     * @param collection          The name of the collection to get
     * @param establishedListener Listener used to listen for connection state changes
     * @return The current database instance
     */
    public Database getCollection(String collection, OnConnectionEstablishedListener establishedListener) throws ConnectionException {
        try {
            if (establishedListener != null) {
                MongoCollection<Document> mongoCollection = client.getDatabase(name).getCollection(collection);
                result = establishedListener.onEstablished(mongoCollection);
            }

        } catch (Exception e) {
            throw new ConnectionException(e);
        }

        return this;
    }

    /**
     * Closes the database connection. The database should be disconnection on server shutdown
     */
    public Database disconnect() {
        client.close();
        return this;
    }

    public ResourceSet getResult() {
        return result;
    }
}
