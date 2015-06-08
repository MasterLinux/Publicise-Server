package net.apetheory.publicise.server.api.documentation.converter;

import flexjson.JSONSerializer;
import net.apetheory.publicise.server.api.documentation.model.DocumentationModel;
import net.apetheory.publicise.server.api.documentation.model.ResourceModel;

public class JsonConverter {

    /**
     * Converts a resource documentation model into its JSON representation
     *
     * @param value The resource documentation model to convert
     * @return A JSON formatted String representing the resource documentation
     */
    public static String toJSON(ResourceModel value, boolean prettyPrint) {
        return toJson(value, prettyPrint);
    }

    /**
     * Converts a documentation model into its JSON representation
     *
     * @param value The documentation model to convert
     * @return A JSON formatted String representing the documentation
     */
    public static String toJSON(DocumentationModel value, boolean prettyPrint) {
        return toJson(value, prettyPrint);
    }

    private static String toJson(Object value, boolean prettyPrint) {
        return new JSONSerializer()
                .prettyPrint(prettyPrint)
                .exclude("*.class")
                .serialize(value);
    }

}
