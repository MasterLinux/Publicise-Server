package net.apetheory.publicise.server.data.converter;

import net.apetheory.publicise.server.data.database.meta.DatabaseField;
import net.apetheory.publicise.server.data.database.meta.DatabaseId;
import net.apetheory.publicise.server.resource.BaseResource;
import org.bson.types.ObjectId;
import org.bson.Document;

import java.lang.reflect.Field;

/**
 * A helper class to convert a resource to a
 * MongoDB database object and back
 */
public class DocumentConverter {

    /**
     * Converts a resource to a MongoDB database document
     * @param resource The resource to convert
     * @return The database object or null on error
     */
    public static <TResource extends BaseResource> Document toDocument(TResource resource) {
        return createDocument(resource, new Document(), resource.getClass());
    }

    private static <TResource extends BaseResource> Document createDocument(TResource resource, Document result, Class<?> clz) {
        //TODO get fields from super classes
        for(Field field : clz.getDeclaredFields()) {
            if(field.isAnnotationPresent(DatabaseField.class)) {
                try {
                    field.setAccessible(true);
                    result.append(field.getName(), field.get(resource));

                } catch (IllegalAccessException e) {
                    //TODO LOG error
                    result = null;
                    break;
                }
            }
        }

        if(clz.getSuperclass() != null) {
            result = createDocument(resource, result, clz.getSuperclass());
        }

        return result;
    }

    /**
     * Converts a MongoDB database document to a resource
     * @param document The MongoDB database object to convert
     * @return The response or null on error
     */
    public static <TResource extends BaseResource>TResource toResource(Class<TResource> resourceClass, Document document) {
        TResource resource = null;

        try {
            resource = insertDBValues(resourceClass, document, resourceClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return resource;
    }

    private static <TResource extends BaseResource>TResource insertDBValues(Class<?> resourceClass, Document document, TResource resource) {
        for(Field field : resourceClass.getDeclaredFields()) {

            //check whether the field is a database field
            if(field.isAnnotationPresent(DatabaseField.class)) {
                try {
                    field.setAccessible(true);
                    field.set(resource, document.get(field.getName()));

                } catch (IllegalAccessException e) {
                    //TODO LOG error
                    break;
                }
            }

            //check whether the field is an ID
            else if(field.isAnnotationPresent(DatabaseId.class)) {
                try {
                    field.setAccessible(true);
                    Object id = document.get("_id");

                    if(id instanceof ObjectId) {
                        field.set(resource, id.toString());
                    } else {
                        //TODO throw db exception 500?
                    }

                } catch (IllegalAccessException e) {
                    //TODO LOG error
                    break;
                }
            }
        }

        if(resourceClass.getSuperclass() != null) {
            resource = insertDBValues(resourceClass.getSuperclass(), document, resource);
        }

        return resource;
    }
}
