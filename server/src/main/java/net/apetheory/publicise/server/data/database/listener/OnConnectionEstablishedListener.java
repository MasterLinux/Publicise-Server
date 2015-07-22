package net.apetheory.publicise.server.data.database.listener;

import com.mongodb.client.MongoCollection;
import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.database.exception.InsertionException;
import org.bson.Document;
import org.jetbrains.annotations.Nullable;

/**
 * Listener used to notify a listener that the
 * connection is established
 */
public interface OnConnectionEstablishedListener {

    /**
     * Callback which is called if a the connection
     * to the database was successful
     *
     * @param collection The collection to insert, etc data
     * @return The ResourceSet or null
     */
    @Nullable
    ResourceSet onEstablished(MongoCollection<Document> collection) throws InsertionException;
}
