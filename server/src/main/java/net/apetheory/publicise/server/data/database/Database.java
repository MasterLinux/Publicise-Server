package net.apetheory.publicise.server.data.database;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import net.apetheory.publicise.server.Config;
import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.database.listener.OnConnectionErrorListener;
import net.apetheory.publicise.server.data.database.listener.OnConnectionEstablishedListener;

import java.net.UnknownHostException;

/**
 * Helper class to establish a connection
 * to the mongoDB database
 */
public class Database {
    private final String host;
    private final int port;
    private MongoClient client;
    private ResourceSet result;

    /**
     * Name of the database
     */
    public static final String NAME = "publicise";

    /**
     * Initializes the database
     *
     * @param host The host of the database to connect
     * @param port The port of the database to connect
     */
    public Database(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Creates a new database configured by a config
     * @param config The config to configure the database
     * @return The new database instance
     */
    public static Database fromConfig(Config config) {
        return new Database(config.getDatabaseHost(), config.getDatabasePort());
    }

    /**
     * Connects to the database
     *
     * @param collection The collection to get after the connection is established
     * @param establishedListener Listener to listen for connection state changes
     * @param errorListener Listener to listen for errors
     * @return The current database instance
     */
    public Database connect(Collection collection, OnConnectionEstablishedListener establishedListener, OnConnectionErrorListener errorListener) {
        try {
            client = new MongoClient(host, port);

            if(establishedListener != null) {
                DB database = client.getDB(NAME);
                DBCollection coll = database.getCollection(collection.getName());

                result = establishedListener.onEstablished(database, coll);
            }

        } catch (UnknownHostException e) {
            //TODO log error

            if(errorListener != null) {
                errorListener.onError(Error.UnknownHost);
            }
        }

        return this;
    }

    /**
     * Closes the database connection
     */
    public Database disconnect() {
        if(client != null) {
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
    public enum Collection {
        Documents("Documents");
        private final String name;

        Collection(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Collection of possible errors
     */
    public enum Error {
        UnknownHost
    }

}
