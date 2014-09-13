package net.apetheory.publicise.server.data.database.dao;

import com.mongodb.*;
import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.database.Database;
import net.apetheory.publicise.server.data.database.listener.OnTransactionErrorListener;
import net.apetheory.publicise.server.resource.DocumentResource;
import org.bson.types.ObjectId;

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
     * @param errorListener A listener which is called if an error is occurred during the transaction
     */
    public static ResourceSet insertInto(Database database, DocumentResource document, OnTransactionErrorListener errorListener) {
        return database.connect(Database.Collection.Documents, (database1, collection) -> {
            BasicDBObject dbObj = new BasicDBObject() //TODO implement a generic version based on annotations and reflection
                    .append("title", document.getTitle())
                    .append("subtitle", document.getSubtitle());

            try {
                collection.insert(dbObj, WriteConcern.SAFE);
            } catch(MongoException e) {
                if(errorListener != null) { //TODO handle error globally?
                    errorListener.onError(); //TODO pass error code
                }

                return null;
            }

            document.setId(dbObj.getObjectId("_id").toString());

            return new ResourceSet
                    .Builder<DocumentResource>(collection.count())
                    .addResource(document)
                    .build();

        }, (error) -> {
            //TODO handle error
        }).disconnect().getResult();
    }

    public static ResourceSet getByIdFrom(Database database, String documentId, OnTransactionErrorListener errorListener) {
        return database.connect(Database.Collection.Documents, (database1, collection) -> {
            if(ObjectId.isValid(documentId)) {
                ObjectId id = new ObjectId(documentId);

                DBObject result = collection.findOne(new BasicDBObject("_id", new BasicDBObject("$eq", id)));

                if(result != null) {
                    DocumentResource resource = new DocumentResource();

                    resource.setId(documentId);
                    resource.setTitle((String) result.get("title"));
                    resource.setSubtitle((String) result.get("subtitle"));

                    return new ResourceSet
                            .Builder<DocumentResource>(collection.count())
                            .addResource(resource)
                            .build();
                }
            }

            return null;

        }, (error) -> {
            //TODO handle error
        }).disconnect().getResult();
    }

    public static ResourceSet getFrom(Database database, OnTransactionErrorListener errorListener) {
        return database.connect(Database.Collection.Documents, (database1, collection) -> {
            DBCursor resultSet = collection.find();

            ResourceSet.Builder<DocumentResource> builder = new ResourceSet.Builder<>(collection.count());
            DocumentResource resource;
            DBObject obj;

            while(resultSet.hasNext()) {
                obj = resultSet.next();
                resource = new DocumentResource();

                resource.setId(obj.get("_id").toString());
                resource.setTitle(obj.get("title").toString());
                resource.setSubtitle(obj.get("subtitle").toString());

                builder.addResource(resource);
            }

            return builder.build();

        }, (error) -> {
            //TODO handle error
        }).disconnect().getResult();
    }
}
