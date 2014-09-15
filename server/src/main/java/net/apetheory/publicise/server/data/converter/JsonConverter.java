package net.apetheory.publicise.server.data.converter;

import flexjson.JSONSerializer;
import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.StringUtils;

/**
 * Helper class to convert a ResourceSet to it JSON representation
 */
public class JsonConverter {

    /**
     * Converts a ResourceSet into its JSON representation
     *
     * @param value The ResourceSet to convert
     * @param fieldsQuery Value of the fields query parameter
     * @return A JSON formatted String representing the ResourceSet
     */
    public static String toJSON(ResourceSet value, String fieldsQuery) {
        JSONSerializer serializer = new JSONSerializer();

        if(!StringUtils.isEmpty(fieldsQuery)) {
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
