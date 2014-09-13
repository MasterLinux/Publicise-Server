package net.apetheory.publicise.server.data.database.dao;

import com.mongodb.*;
import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.database.Database;
import net.apetheory.publicise.server.data.database.listener.OnTransactionCompletedListener;
import net.apetheory.publicise.server.data.database.listener.OnTransactionErrorListener;
import net.apetheory.publicise.server.resource.DocumentResource;

/**
 * Data access object which is used to insert,
 * update, delete and get documents
 */
public class DocumentsDAO {

    /**
     * Inserts a new document into the database
     *
     * @param database The database to insert the new document
     * @param document The document to insert
     * @param completedListener A listener which is called if the transaction was successful
     * @param errorListener A listener which is called if an error is occurred during the transaction
     */
    public static void insertInto(Database database, DocumentResource document, OnTransactionCompletedListener completedListener, OnTransactionErrorListener errorListener) {
        database.connect(Database.Collection.Documents, (database1, collection) -> {
            BasicDBObject dbObj = new BasicDBObject() //TODO implement a generic version based on annotations and reflection
                    .append("title", document.getTitle())
                    .append("subtitle", document.getSubtitle());

            try {
                collection.insert(dbObj, WriteConcern.SAFE);
            } catch(MongoException e) {
                if(errorListener != null) { //TODO handle error globally?
                    errorListener.onError(); //TODO pass error code
                }

                return true;
            }

            if(completedListener != null) {
                document.setId(dbObj.getObjectId("_id").toString());

                completedListener.onCompleted(
                        new ResourceSet
                                .Builder<DocumentResource>(collection.count())
                                .addResource(document)
                                .build()
                );
            }

            return true;
        }, (error) -> {
            //TODO handle error
        });
    }

    public static void getFrom(Database database, String documentId, OnTransactionCompletedListener completedListener, OnTransactionErrorListener errorListener) {
        database.connect(Database.Collection.Documents, (database1, collection) -> {
            return true;
        }, (error) -> {
            //TODO handle error
        });
    }
}
