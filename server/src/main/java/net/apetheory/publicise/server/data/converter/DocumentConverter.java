package net.apetheory.publicise.server.data.converter;

import net.apetheory.publicise.server.data.converter.exception.MissingValueException;
import net.apetheory.publicise.server.data.converter.exception.ReadOnlyException;
import net.apetheory.publicise.server.data.database.meta.DatabaseField;
import net.apetheory.publicise.server.data.database.meta.DatabaseId;
import net.apetheory.publicise.server.data.database.meta.Visibility;
import net.apetheory.publicise.server.resource.BaseResource;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;

/**
 * A helper class to convert a resource to a
 * MongoDB database object and back
 */
public class DocumentConverter {

    /**
     * Mode which defines the document creation strategy.
     */
    public enum Mode {
        CREATE, UPDATE, DELETE
    }

    public interface OnDocumentCreatedListener {
        void onDocumentCreated(Document document, Exception exception);
    }

    /**
     * Converts a resource to a MongoDB database document
     *
     * @param resource The resource to convert
     * @return The database object or null on error
     */
    public static <TResource extends BaseResource> void toDocument(TResource resource, OnDocumentCreatedListener listener) {
        toDocument(resource, Mode.CREATE, listener);
    }

    public static <TResource extends BaseResource> void toDocument(TResource resource, Mode mode, OnDocumentCreatedListener listener) {
        try {
            Document document = createDocument(resource, new Document(), resource.getClass(), mode);
            listener.onDocumentCreated(document, null);
        } catch (MissingValueException | ReadOnlyException e) {
            listener.onDocumentCreated(null, e);
        }
    }

    private static <TResource extends BaseResource> Document createDocument(TResource resource, Document result, Class<?> clz, Mode mode) throws MissingValueException, ReadOnlyException {
        //TODO get fields from super classes
        for (Field field : clz.getDeclaredFields()) {
            if (field.isAnnotationPresent(DatabaseField.class)) {
                DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
                boolean isWritable = !(databaseField.isFinal() && mode == Mode.UPDATE);

                if (isWritable) {
                    boolean isAccessible = field.isAccessible();
                    boolean isRequired = databaseField.isRequired();

                    try {
                        field.setAccessible(true);
                        Object value = field.get(resource);

                        if (isRequired && value == null) {
                            throw new MissingValueException();

                        } else {
                            result.append(field.getName(), value);
                        }

                    } catch (IllegalAccessException e) {
                        //TODO throw exception?
                        result = null;
                        break;

                    } finally {
                        field.setAccessible(isAccessible);
                    }
                } else {
                    throw new ReadOnlyException(field.getName());
                }
            }
        }

        if (clz.getSuperclass() != null) {
            result = createDocument(resource, result, clz.getSuperclass(), mode);
        }

        return result;
    }

    /**
     * Converts a MongoDB database document to a resource
     *
     * @param document The MongoDB database object to convert
     * @return The response or null on error
     */
    public static <TResource extends BaseResource> TResource toResource(Class<TResource> resourceClass, Document document) {
        TResource resource = null;

        try {
            resource = insertDBValues(resourceClass, document, resourceClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return resource;
    }

    private static <TResource extends BaseResource> TResource insertDBValues(Class<?> resourceClass, Document document, TResource resource) {
        for (Field field : resourceClass.getDeclaredFields()) {

            //check whether the field is a database field
            if (field.isAnnotationPresent(DatabaseField.class)) {
                DatabaseField databaseField = field.getAnnotation(DatabaseField.class);

                if (databaseField.visibility() == Visibility.VISIBLE) {
                    boolean isAccessible = field.isAccessible();

                    try {
                        field.setAccessible(true);
                        field.set(resource, document.get(field.getName()));

                    } catch (IllegalAccessException e) {
                        //TODO LOG error
                        break;

                    } finally {
                        field.setAccessible(isAccessible);
                    }
                }
            }

            //check whether the field is an ID
            else if (field.isAnnotationPresent(DatabaseId.class)) {
                boolean isAccessible = field.isAccessible();

                try {
                    field.setAccessible(true);
                    Object id = document.get("_id");

                    if (id instanceof ObjectId) {
                        field.set(resource, id.toString());
                    } else {
                        //TODO throw db exception 500?
                    }

                } catch (IllegalAccessException e) {
                    //TODO LOG error
                    break;

                } finally {
                    field.setAccessible(isAccessible);
                }
            }
        }

        if (resourceClass.getSuperclass() != null) {
            resource = insertDBValues(resourceClass.getSuperclass(), document, resource);
        }

        return resource;
    }
}
