package net.apetheory.publicise.server.data.database.dao;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;
import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.converter.DocumentConverter;
import net.apetheory.publicise.server.data.database.Database;
import net.apetheory.publicise.server.data.database.error.InsertionError;
import net.apetheory.publicise.server.data.database.listener.OnTransactionErrorListener;
import net.apetheory.publicise.server.resource.DocumentResource;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.ws.rs.core.UriInfo;

import static com.mongodb.client.model.Filters.eq;

/**
 * Data access object which is used to insert,
 * update, delete and get documents
 */
public class DocumentsDAO {

    /**
     * Inserts a new document into the database
     *
     * @param database The database to insert the new document
     * @param document The document resource to insert
     * @param errorListener A listener which is called if an error is occurred during the transaction
     */
    public static ResourceSet insertInto(Database database, DocumentResource document, OnTransactionErrorListener errorListener) {
        return database.connect(Database.Collection.Documents, (collection) -> {
            Document dbObj = DocumentConverter.toDocument(document);

            try {
                collection.insertOne(dbObj);
            } catch(MongoException e) {
                if(errorListener != null) {
                    errorListener.onError(new InsertionError());
                }

                return null;
            }

            document.setId(dbObj.getObjectId("_id").toString());

            return new ResourceSet
                    .Builder<DocumentResource>(collection.count())
                    .addResource(document)
                    .build();

        }, (error) -> {
            if(errorListener != null) {
                errorListener.onError(error);
            }
        }).disconnect().getResult();
    }

    public static ResourceSet getByIdFrom(Database database, String documentId, OnTransactionErrorListener errorListener) {
        return database.connect(Database.Collection.Documents, (collection) -> {
            if(ObjectId.isValid(documentId)) {
                ObjectId id = new ObjectId(documentId);

                Document result = collection.find(eq("_id", id)).first();

                if(result != null) {
                    DocumentResource resource = DocumentConverter.toResource(DocumentResource.class, result);

                    return new ResourceSet
                            .Builder<DocumentResource>(collection.count())
                            .setFilteredCount(1)
                            .addResource(resource)
                            .build();
                }
            }

            return null;

        }, (error) -> {
            if(errorListener != null) {
                errorListener.onError(error);
            }
        }).disconnect().getResult();
    }

    public static ResourceSet getFrom(Database database, UriInfo uriInfo, int offset, int limit, OnTransactionErrorListener errorListener) {
        return database.connect(Database.Collection.Documents, (collection) -> {
            MongoCursor<Document> resultSet;

            long count = collection.count();
            int startIdx = limit * offset;

            ResourceSet.Builder<DocumentResource> builder =
                    new ResourceSet.Builder<DocumentResource>(count)
                        .setFilteredCount(count)
                        .setUriInfo(uriInfo)
                        .setOffset(offset)
                        .setLimit(limit);

            if(startIdx < count) {
                resultSet = collection.find().skip(startIdx).limit(limit).iterator();

                while (resultSet.hasNext()) {
                    builder.addResource(DocumentConverter.toResource(DocumentResource.class, resultSet.next()));
                }
            }

            return builder.build();

        }, (error) -> {
            if(errorListener != null) {
                errorListener.onError(error);
            }
            //TODO handle error
        }).disconnect().getResult();
    }
}
