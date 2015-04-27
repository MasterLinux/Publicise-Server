package net.apetheory.publicise.server.data.converter;

import flexjson.JSONSerializer;
import net.apetheory.publicise.server.data.ResourceSet;
import org.jetbrains.annotations.NotNull;

/**
 * Helper class to convert a ResourceSet to it JSON representation
 */
public class JsonConverter {

    /**
     * Converts a ResourceSet into its JSON representation
     *
     * @param value The ResourceSet to convert
     * @param fields Collection of required fields to return in response
     * @return A JSON formatted String representing the ResourceSet
     */
    public static String toJSON(ResourceSet value, @NotNull String[] fields, boolean prettyPrint) {
        JSONSerializer serializer = new JSONSerializer();

        if(fields.length > 0) {
            for (String field : fields) {
                serializer.include("objects." + field);
            }

            serializer.exclude("objects.*");
        } else {
            serializer.include("objects");
        }

        return serializer
                .prettyPrint(prettyPrint)
                .exclude("*.class")
                .serialize(value);
    }

}
