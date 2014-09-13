package net.apetheory.publicise.server.data.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import net.apetheory.publicise.server.data.database.meta.DatabaseField;
import net.apetheory.publicise.server.data.database.meta.DatabaseId;
import net.apetheory.publicise.server.resource.BaseResource;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;

/**
 * A helper class to convert a resource to a
 * MongoDB database object and back
 */
public class DBObjectConverter {

    /**
     * Converts a resource to a MongoDB database object
     * @param resource The resource to convert
     * @return The database object or null on error
     */
    public static <TResource extends BaseResource>BasicDBObject toDBObject(TResource resource) {
        return createBasicDBObject(resource, new BasicDBObject(), resource.getClass());
    }

    private static <TResource extends BaseResource> BasicDBObject createBasicDBObject(TResource resource, BasicDBObject result, Class<?> clz) {
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
            result = createBasicDBObject(resource, result, clz.getSuperclass());
        }

        return result;
    }

    /**
     * Converts a MongoDB database object to a resource
     * @param dbObject The MongoDB database object to convert
     * @return The response or null on error
     */
    public static <TResource extends BaseResource>TResource toResource(Class<TResource> resourceClass, DBObject dbObject) {
        TResource resource = null;

        try {
            resource = insertDBValues(resourceClass, dbObject, resourceClass.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return resource;
    }

    private static <TResource extends BaseResource>TResource insertDBValues(Class<?> resourceClass, DBObject dbObject, TResource resource) {
        for(Field field : resourceClass.getDeclaredFields()) {

            //check whether the field is a database field
            if(field.isAnnotationPresent(DatabaseField.class)) {
                try {
                    field.setAccessible(true);
                    field.set(resource, dbObject.get(field.getName()));

                } catch (IllegalAccessException e) {
                    //TODO LOG error
                    break;
                }
            }

            //check whether the field is an ID
            else if(field.isAnnotationPresent(DatabaseId.class)) {
                try {
                    field.setAccessible(true);
                    Object id = dbObject.get("_id");

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
            resource = insertDBValues(resourceClass.getSuperclass(), dbObject, resource);
        }

        return resource;
    }
}
