package net.apetheory.publicise.server.data.database;

import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.converter.DocumentConverter;
import net.apetheory.publicise.server.data.database.exception.ConnectionException;
import net.apetheory.publicise.server.data.database.exception.InsertionException;
import net.apetheory.publicise.server.data.database.exception.QueryException;
import net.apetheory.publicise.server.resource.BaseResource;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.ws.rs.core.UriInfo;

import static com.mongodb.client.model.Filters.eq;

/**
 * A provider used to get access to a specific resource
 */
public class ResourceProvider<TResource extends BaseResource> {
    private final Class<TResource> resourceClass;
    private final String collection;
    private final Database database;

    public interface OnCompletedListener {
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
    public void insert(final TResource resource, @NotNull final OnCompletedListener listener) {
        database.getCollection(collection, (collection) -> {
            final Document dbObj = DocumentConverter.toDocument(resource);

            collection.insertOne(dbObj, (noResult, insertionThrowable) -> {
                if (insertionThrowable != null) {
                    listener.onDataSetInserted(null, new InsertionException(insertionThrowable));
                    return;
                }

                collection.count((count, countThrowable) -> {
                    if (countThrowable != null) {
                        listener.onDataSetInserted(null, new InsertionException(countThrowable));
                        return;
                    }

                    resource.setId(dbObj.getObjectId("_id").toString());
                    listener.onDataSetInserted(new ResourceSet.Builder<TResource>(count)
                            .addResource(resource)
                            .setFilteredCount(1)
                            .build(), null);
                });
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
    public void getById(final String resourceId, @NotNull final OnCompletedListener listener) {
        if (ObjectId.isValid(resourceId)) {
            database.getCollection(collection, (collection) -> {
                final ObjectId id = new ObjectId(resourceId);

                collection.find(eq("_id", id)).first((result, findThrowable) -> {
                    if (findThrowable != null) {
                        listener.onDataSetInserted(null, new QueryException(findThrowable));
                    }

                    collection.count((count, countThrowable) -> {
                        if (countThrowable != null) {
                            listener.onDataSetInserted(null, new QueryException(countThrowable));
                            return;
                        }

                        TResource resource = DocumentConverter.toResource(resourceClass, result);
                        listener.onDataSetInserted(new ResourceSet.Builder<TResource>(count)
                                .setFilteredCount(1)
                                .addResource(resource)
                                .build(), null);
                    });
                });
            });
        } else {
            listener.onDataSetInserted(null, new QueryException(null)); //TODO create specific exception for invalid IDs?
        }
    }

    public void get(final UriInfo uriInfo, final int offset, final int limit, @NotNull final OnCompletedListener listener) {
        database.getCollection(collection, (collection) -> {
            collection.count((count, countThrowable) -> {
                if (countThrowable != null) {
                    listener.onDataSetInserted(null, new QueryException(countThrowable));
                    return;
                }

                final int startIdx = limit * offset;
                final ResourceSet.Builder<TResource> builder =
                        new ResourceSet.Builder<TResource>(count)
                                .setFilteredCount(count)
                                .setUriInfo(uriInfo)
                                .setOffset(offset)
                                .setLimit(limit);

                if (startIdx < count) {
                    collection.find().skip(startIdx).limit(limit).forEach(document -> {
                        builder.addResource(DocumentConverter.toResource(resourceClass, document));
                    }, (noResult, findThrowable) -> {
                        if (findThrowable != null) {
                            listener.onDataSetInserted(null, new QueryException(findThrowable));
                            return;
                        }

                        listener.onDataSetInserted(builder.build(), null);
                    });
                } else {
                    listener.onDataSetInserted(builder.build(), null);
                }
            });
        });
    }
}
