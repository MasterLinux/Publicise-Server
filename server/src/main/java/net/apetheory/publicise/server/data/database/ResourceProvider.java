package net.apetheory.publicise.server.data.database;

import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.converter.DocumentConverter;
import net.apetheory.publicise.server.data.database.exception.*;
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

    /**
     * Interface for listener which is invoked whenever a database transaction is completed
     */
    public interface OnTransactionCompletedListener {

        /**
         * Callback method which is called whenever the transaction completes
         * @param resourceSet The resource set or null on error
         * @param exception Null on success or the specific exception which occurred while requesting
         */
        void onTransactionCompleted(@Nullable ResourceSet resourceSet, @Nullable Exception exception);
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
    public void insert(final TResource resource, @NotNull final OnTransactionCompletedListener listener) {
        database.getCollection(collection, (collection) -> {
            DocumentConverter.toDocument(resource, (document, exception) -> {
                if (exception != null) {
                    listener.onTransactionCompleted(null, new InsertionException(exception));
                    return;
                }

                collection.insertOne(document, (noResult, insertionThrowable) -> {
                    if (insertionThrowable != null) {
                        listener.onTransactionCompleted(null, new InsertionException(insertionThrowable));
                        return;
                    }

                    collection.count((count, countThrowable) -> {
                        if (countThrowable != null) {
                            listener.onTransactionCompleted(null, new InsertionException(countThrowable));
                            return;
                        }

                        resource.setId(document.getObjectId("_id").toString());
                        listener.onTransactionCompleted(new ResourceSet.Builder<TResource>(count)
                                .addResource(resource)
                                .setFilteredCount(1)
                                .build(), null);
                    });
                });
            });
        });
    }

    /**
     * Deletes a specific resource by its ID
     *
     * @param resourceId The ID of the resource to delete
     * @param listener   Callback which is executed whenever the transaction completes
     */
    public void deleteById(final String resourceId, @NotNull final OnTransactionCompletedListener listener) {
        if (ObjectId.isValid(resourceId)) {
            database.getCollection(collection, (collection) -> {
                final ObjectId id = new ObjectId(resourceId);

                // get resource first
                collection.find(eq("_id", id)).first((findResult, findThrowable) -> {
                    if (findThrowable != null) {
                        listener.onTransactionCompleted(null, new DeleteException(findThrowable));
                        return;
                    }

                    // if found delete from database
                    if (findResult.size() > 0) {
                        collection.deleteOne(eq("_id", id), (result, deleteThrowable) -> {
                            if (deleteThrowable != null) {
                                listener.onTransactionCompleted(null, new DeleteException(deleteThrowable));
                                return;
                            }

                            // get count if deleted
                            if (result.getDeletedCount() > 0) {
                                collection.count((count, countThrowable) -> {
                                    if (countThrowable != null) {
                                        listener.onTransactionCompleted(null, new DeleteException(countThrowable));
                                        return;
                                    }

                                    TResource resource = DocumentConverter.toResource(resourceClass, findResult);
                                    listener.onTransactionCompleted(new ResourceSet.Builder<TResource>(count)
                                            .setFilteredCount(1)
                                            .addResource(resource)
                                            .build(), null);
                                });
                            } else {
                                listener.onTransactionCompleted(null, new DeleteException(null)); //TODO throw specific exception
                            }
                        });
                    } else {
                        listener.onTransactionCompleted(null, new NotFoundException(resourceId));
                    }
                });
            });
        } else {
            listener.onTransactionCompleted(null, new InvalidIdException(resourceId));
        }
    }

    /**
     * Gets a specific resource by its ID
     *
     * @param resourceId The ID of the resource to get
     * @return The requested resource or null
     * @throws ConnectionException
     */
    public void getById(final String resourceId, @NotNull final OnTransactionCompletedListener listener) {
        if (ObjectId.isValid(resourceId)) {
            database.getCollection(collection, (collection) -> {
                final ObjectId id = new ObjectId(resourceId);

                collection.find(eq("_id", id)).first((result, findThrowable) -> {
                    if (findThrowable != null) {
                        listener.onTransactionCompleted(null, new QueryException(findThrowable));
                        return;
                    }

                    collection.count((count, countThrowable) -> {
                        if (countThrowable != null) {
                            listener.onTransactionCompleted(null, new QueryException(countThrowable));
                            return;
                        }

                        TResource resource = DocumentConverter.toResource(resourceClass, result);
                        listener.onTransactionCompleted(new ResourceSet.Builder<TResource>(count)
                                .setFilteredCount(1)
                                .addResource(resource)
                                .build(), null);
                    });
                });
            });
        } else {
            listener.onTransactionCompleted(null, new InvalidIdException(resourceId));
        }
    }

    public void get(final UriInfo uriInfo, final int offset, final int limit, @NotNull final OnTransactionCompletedListener listener) {
        database.getCollection(collection, (collection) -> {
            collection.count((count, countThrowable) -> {
                if (countThrowable != null) {
                    listener.onTransactionCompleted(null, new QueryException(countThrowable));
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
                            listener.onTransactionCompleted(null, new QueryException(findThrowable));
                            return;
                        }

                        listener.onTransactionCompleted(builder.build(), null);
                    });
                } else {
                    listener.onTransactionCompleted(builder.build(), null);
                }
            });
        });
    }

    public void updateById(final String resourceId, final TResource resource, @NotNull final OnTransactionCompletedListener listener) {
        if (ObjectId.isValid(resourceId)) {
            database.getCollection(collection, (collection) -> {
                final ObjectId id = new ObjectId(resourceId);

                DocumentConverter.toDocument(resource, DocumentConverter.Mode.UPDATE, (document, exception) -> {
                    if (exception != null) {
                        listener.onTransactionCompleted(null, new UpdateException(exception));
                        return;
                    }

                    collection.updateOne(eq("_id", id), new Document("$set", document), (updateResult, updateThrowable) -> {
                        if (updateThrowable != null) {
                            listener.onTransactionCompleted(null, new UpdateException(updateThrowable));
                            return;
                        }

                        collection.find(eq("_id", id)).first((result, findThrowable) -> {
                            if (findThrowable != null) {
                                listener.onTransactionCompleted(null, new UpdateException(findThrowable));
                                return;
                            }

                            collection.count((count, countThrowable) -> {
                                if (countThrowable != null) {
                                    listener.onTransactionCompleted(null, new UpdateException(countThrowable));
                                    return;
                                }

                                TResource updatedResource = DocumentConverter.toResource(resourceClass, result);
                                listener.onTransactionCompleted(new ResourceSet.Builder<TResource>(count)
                                        .setFilteredCount(1)
                                        .addResource(updatedResource)
                                        .build(), null);
                            });
                        });
                    });
                });
            });
        } else {
            listener.onTransactionCompleted(null, new InvalidIdException(resourceId));
        }
    }
}
