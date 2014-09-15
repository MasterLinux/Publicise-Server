package net.apetheory.publicise.server.data.converter;

import flexjson.JSONSerializer;
import net.apetheory.publicise.server.data.ResourceSet;

/**
 * Created by Christoph on 14.09.2014.
 */
public class JsonConverter {

    /**
     * Converts a ResourceSet into its JSON representation
     *
     * @param value The ResourceSet to convert
     * @param fieldsQuery Value of the fields query parameter
     * @return A JSON formatted String representing the ResourceSet
     */
    public String toJSON(ResourceSet value, String fieldsQuery) {
        JSONSerializer serializer = new JSONSerializer();

        if(fieldsQuery != null && fieldsQuery.length() > 0) {
            String[] fields = fieldsQuery.split(",");

            for (String field : fields) {
                serializer.include("objects." + field);
            }

            serializer.exclude("objects.*");
        } else {
            serializer.include("objects");
        }

        return serializer
                .exclude("*.class")
                .serialize(value);
    }

}
