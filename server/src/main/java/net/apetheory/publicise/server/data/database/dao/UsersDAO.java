package net.apetheory.publicise.server.data.database.dao;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;
import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.converter.DocumentConverter;
import net.apetheory.publicise.server.data.database.Database;
import net.apetheory.publicise.server.data.database.error.InsertionError;
import net.apetheory.publicise.server.data.database.listener.OnTransactionErrorListener;
import net.apetheory.publicise.server.resource.UserResource;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.ws.rs.core.UriInfo;

import static com.mongodb.client.model.Filters.eq;

/**
 * Data access object which is used to insert,
 * update, delete and get users
 */
public class UsersDAO {

    /**
     * Inserts a new user into the database
     *
     * @param database The database to insert the new user
     * @param user The user resource to insert
     * @param errorListener A listener which is called if an error is occurred during the transaction
     */
    public static ResourceSet insertInto(Database database, UserResource user, OnTransactionErrorListener errorListener) {
        return database.connect(Database.Collection.Users, (collection) -> {
            Document dbObj = DocumentConverter.toDocument(user);

            try {
                collection.insertOne(dbObj);
            } catch(MongoException e) {
                if(errorListener != null) {
                    errorListener.onError(new InsertionError());
                }

                return null;
            }

            user.setId(dbObj.getObjectId("_id").toString());

            return new ResourceSet
                    .Builder<UserResource>(collection.count())
                    .addResource(user)
                    .build();

        }, (error) -> {
            if(errorListener != null) {
                errorListener.onError(error);
            }
        }).disconnect().getResult();
    }

    public static ResourceSet getByIdFrom(Database database, String userId, OnTransactionErrorListener errorListener) {
        return database.connect(Database.Collection.Users, (collection) -> {
            if(ObjectId.isValid(userId)) {
                ObjectId id = new ObjectId(userId);

                Document result = collection.find(eq("_id", id)).first();

                if(result != null) {
                    UserResource resource = DocumentConverter.toResource(UserResource.class, result);

                    return new ResourceSet
                            .Builder<UserResource>(collection.count())
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
        return database.connect(Database.Collection.Users, (collection) -> {
            MongoCursor<Document> resultSet;

            long count = collection.count();
            int startIdx = limit * offset;

            ResourceSet.Builder<UserResource> builder =
                    new ResourceSet.Builder<UserResource>(count)
                        .setFilteredCount(count)
                        .setUriInfo(uriInfo)
                        .setOffset(offset)
                        .setLimit(limit);

            if(startIdx < count) {
                resultSet = collection.find().skip(startIdx).limit(limit).iterator();

                while (resultSet.hasNext()) {
                    builder.addResource(DocumentConverter.toResource(UserResource.class, resultSet.next()));
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
