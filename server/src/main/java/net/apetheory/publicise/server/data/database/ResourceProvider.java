package net.apetheory.publicise.server.data.database;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;
import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.converter.DocumentConverter;
import net.apetheory.publicise.server.data.database.exception.ConnectionException;
import net.apetheory.publicise.server.data.database.exception.InsertionException;
import net.apetheory.publicise.server.resource.BaseResource;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.ws.rs.core.UriInfo;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Christoph on 12.06.2015.
 */
public class ResourceProvider<TResource extends BaseResource> {
    private final Class<TResource> resourceClass;
    private final String collection;
    private final Database database;

    /**
     * Initializes the content provider
     *
     * @param database      The database to insert the new user
     * @param collection    The name of the collection to access
     * @param resourceClass The class of the resource
     */
    public ResourceProvider(Database database, String collection, Class<TResource> resourceClass) {
        this.resourceClass = resourceClass;
        this.collection = collection;
        this.database = database;
    }

    /**
     * Inserts a new resource into the database
     *
     * @param resource The resource to insert
     * @throws InsertionException
     * @throws ConnectionException
     */
    public ResourceSet insert(TResource resource) throws InsertionException, ConnectionException {
        return database.connect(collection, (collection) -> {
            Document dbObj = DocumentConverter.toDocument(resource);

            try {
                collection.insertOne(dbObj);
            } catch (MongoException e) {
                throw new InsertionException(e);
            }

            resource.setId(dbObj.getObjectId("_id").toString());

            return new ResourceSet
                    .Builder<TResource>(collection.count())
                    .addResource(resource)
                    .build();

        }).getResult();
    }

    /**
     * Gets a specific resource by its ID
     *
     * @param resourceId The ID of the resource to get
     * @return The requested resource or null
     * @throws ConnectionException
     */
    public ResourceSet getById(String resourceId) throws ConnectionException {
        return database.connect(collection, (collection) -> {
            if (ObjectId.isValid(resourceId)) {
                ObjectId id = new ObjectId(resourceId);

                Document result = collection.find(eq("_id", id)).first();

                if (result != null) {
                    TResource resource = DocumentConverter.toResource(resourceClass, result);

                    return new ResourceSet
                            .Builder<TResource>(collection.count())
                            .setFilteredCount(1)
                            .addResource(resource)
                            .build();
                }
            }

            return null;

        }).getResult();
    }

    public ResourceSet get(UriInfo uriInfo, int offset, int limit) throws ConnectionException {
        return database.connect(collection, (collection) -> {
            MongoCursor<Document> resultSet;

            long count = collection.count();
            int startIdx = limit * offset;

            ResourceSet.Builder<TResource> builder =
                    new ResourceSet.Builder<TResource>(count)
                            .setFilteredCount(count)
                            .setUriInfo(uriInfo)
                            .setOffset(offset)
                            .setLimit(limit);

            if (startIdx < count) {
                resultSet = collection.find().skip(startIdx).limit(limit).iterator();

                while (resultSet.hasNext()) {
                    builder.addResource(DocumentConverter.toResource(resourceClass, resultSet.next()));
                }
            }

            return builder.build();

        }).getResult();
    }
}
