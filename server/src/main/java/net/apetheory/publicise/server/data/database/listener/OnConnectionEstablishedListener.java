package net.apetheory.publicise.server.data.database.listener;

import com.mongodb.DB;
import com.mongodb.DBCollection;

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
     * @return If true is returned the database connection will be closed,
     * if not it is required to handle the disconnect yourself by calling `Database.disconnect()`
     */
    boolean onEstablished(DB database, DBCollection collection);
}
