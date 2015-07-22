package net.apetheory.publicise.server.data.database.listener;

import com.mongodb.async.client.MongoCollection;
import net.apetheory.publicise.server.data.database.exception.InsertionException;
import org.bson.Document;

/**
 * Listener used to notify a listener that the
 * connection is established
 */
public interface OnConnectionEstablishedListener {

    /**
     * Callback which is called if a the connection
     * to the database was successful
     *
     * @param collection The collection to manipulate
     */
    void onEstablished(MongoCollection<Document> collection) throws InsertionException;
}
