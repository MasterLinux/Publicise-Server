package net.apetheory.publicise.server.data.database.dao;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;
import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.converter.DocumentConverter;
import net.apetheory.publicise.server.data.database.Database;
import net.apetheory.publicise.server.data.database.exception.ConnectionException;
import net.apetheory.publicise.server.data.database.exception.InsertionException;
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
     * @param user     The user resource to insert
     * @throws InsertionException
     * @throws ConnectionException
     */
    public static ResourceSet insertInto(Database database, UserResource user) throws InsertionException, ConnectionException {
        return database.connect(Database.Collection.Users, (collection) -> {
            Document dbObj = DocumentConverter.toDocument(user);

            try {
                collection.insertOne(dbObj);
            } catch (MongoException e) {
                throw new InsertionException(e);
            }

            user.setId(dbObj.getObjectId("_id").toString());

            return new ResourceSet
                    .Builder<UserResource>(collection.count())
                    .addResource(user)
                    .build();

        }).getResult();
    }

    public static ResourceSet getByIdFrom(Database database, String userId) throws ConnectionException {
        return database.connect(Database.Collection.Users, (collection) -> {
            if (ObjectId.isValid(userId)) {
                ObjectId id = new ObjectId(userId);

                Document result = collection.find(eq("_id", id)).first();

                if (result != null) {
                    UserResource resource = DocumentConverter.toResource(UserResource.class, result);

                    return new ResourceSet
                            .Builder<UserResource>(collection.count())
                            .setFilteredCount(1)
                            .addResource(resource)
                            .build();
                }
            }

            return null;

        }).getResult();
    }

    public static ResourceSet getFrom(Database database, UriInfo uriInfo, int offset, int limit) throws ConnectionException {
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

            if (startIdx < count) {
                resultSet = collection.find().skip(startIdx).limit(limit).iterator();

                while (resultSet.hasNext()) {
                    builder.addResource(DocumentConverter.toResource(UserResource.class, resultSet.next()));
                }
            }

            return builder.build();

        }).getResult();
    }
}
