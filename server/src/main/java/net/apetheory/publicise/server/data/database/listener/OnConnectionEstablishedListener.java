package net.apetheory.publicise.server.data.database.listener;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import net.apetheory.publicise.server.data.ResourceSet;

/**
 * Listener used to notify a listener that the
 * connection is established
 */
public interface OnConnectionEstablishedListener {

    /**
     * Callback which is called if a the connection
     * to the database was successful
     *
     * @param database The database to work with
     * @param collection The collection to insert, etc data
     * @return The ResourceSet or null
     */
    ResourceSet onEstablished(DB database, DBCollection collection);
}
