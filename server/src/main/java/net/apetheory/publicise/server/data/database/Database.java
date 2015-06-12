package net.apetheory.publicise.server.data.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
    private final String host;
    private final String name;
    private final int port;
    private final MongoDatabase database;
    private MongoClient client;
    private ResourceSet result;

    /**
     * Initializes the database
     *
     * @param host The host of the database to connect
     * @param port The port of the database to connect
     */
    private Database(String name, String host, int port) {
        this.host = host;
        this.port = port;
        this.name = name;

        client = new MongoClient(host, port);
        database = client.getDatabase(name);
    }

    /**
     * Creates a new database configured by a config
     *
     * @param config The config to configure the database
     * @return The new database instance
     */
    synchronized public static Database fromConfig(Config config) {
        String databaseName = config.getDatabaseName();
        Database database = null;

        if (!instances.containsKey(databaseName)) {
            synchronized (instanceLock) {
                database = new Database(
                        databaseName,
                        config.getDatabaseHost(),
                        config.getDatabasePort()
                );

                instances.put(databaseName, database);
            }
        }

        return database;
    }

    /**
     * Creates a new database configured by the default config
     *
     * @return The new database instance
     */
    public static Database fromConfig() {
        Config config = Config.load();
        return fromConfig(config);
    }

    /**
     * Connects to the database
     *
     * @param collection          The collection to get after the connection is established
     * @param establishedListener Listener to listen for connection state changes
     * @return The current database instance
     */
    public Database connect(Collection collection, OnConnectionEstablishedListener establishedListener) throws ConnectionException {
        try {
            if (establishedListener != null) {
                MongoCollection<Document> mongoCollection = database.getCollection(collection.getName());
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
        if (client != null) {
            client.close();
            client = null;
            //TODO LOG lifecycle database connection closed
        } else {
            //TODO LOG lifecycle database connection is already closed
        }

        return this;
    }

    public ResourceSet getResult() {
        return result;
    }

    /**
     * All available database collections
     */
    public enum Collection {  //TODO remove and use strings instead
        Users("Users");
        private final String name;

        Collection(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
