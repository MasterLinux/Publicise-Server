package net.apetheory.publicise.server.data.database;

import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.converter.DocumentConverter;
import net.apetheory.publicise.server.data.database.exception.ConnectionException;
import net.apetheory.publicise.server.data.database.exception.InsertionException;
import net.apetheory.publicise.server.resource.BaseResource;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.ws.rs.core.UriInfo;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A provider used to get access to a specific resource
 */
public class ResourceProvider<TResource extends BaseResource> {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Class<TResource> resourceClass;
    private final String collection;
    private final Database database;

    public interface OnDataSetInsertedListener {
        void onDataSetInserted(@Nullable ResourceSet resourceSet, @Nullable Exception exception);
    }

    /**
     * Initializes the content provider
     *
     * @param database      The database to insert the new data record
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
    public void insert(final TResource resource, @NotNull final OnDataSetInsertedListener listener) throws InsertionException, ConnectionException {
        database.getCollection(collection, (collection) -> {
            final Document dbObj = DocumentConverter.toDocument(resource);

            collection.insertOne(dbObj, (noResult, insertionError) -> {
                if (insertionError != null) {
                    listener.onDataSetInserted(null, new InsertionException(insertionError));
                } else {
                    collection.count((count, countingError) -> {
                        if (countingError != null) {
                            listener.onDataSetInserted(null, new InsertionException(countingError));
                        } else {
                            resource.setId(dbObj.getObjectId("_id").toString());
                            listener.onDataSetInserted(new ResourceSet
                                    .Builder<TResource>(count)
                                    .addResource(resource)
                                    .setFilteredCount(1)
                                    .build(), null);
                        }
                    });
                }
            });
        });
    }

    /**
     * Gets a specific resource by its ID
     *
     * @param resourceId The ID of the resource to get
     * @return The requested resource or null
     * @throws ConnectionException
     */
    @Nullable
    public ResourceSet getById(String resourceId) throws ConnectionException {
        return database.getCollection(collection, (collection) -> {
            /*
            ResourceSet resourceSet = null;

            if (ObjectId.isValid(resourceId)) {
                final ObjectId id = new ObjectId(resourceId);
                final Lock lock = readWriteLock.readLock();

                lock.lock();
                Document result = collection.find(eq("_id", id)).first();
                lock.unlock();

                if (result != null) {
                    TResource resource = DocumentConverter.toResource(resourceClass, result);

                    resourceSet = new ResourceSet
                            .Builder<TResource>(collection.count())
                            .setFilteredCount(1)
                            .addResource(resource)
                            .build();
                }
            }

            return resourceSet;
            */

        }).getResult();
    }

    public ResourceSet get(UriInfo uriInfo, int offset, int limit) throws ConnectionException {
        return database.getCollection(collection, (collection) -> {
            /*
            final Lock lock = readWriteLock.readLock();
            MongoCursor<Document> resultSet;

            lock.lock();

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

            lock.unlock();

            return builder.build();

            */

        }).getResult();
    }
}
