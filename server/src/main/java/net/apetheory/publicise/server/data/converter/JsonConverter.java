package net.apetheory.publicise.server.data.converter;

import flexjson.JSONSerializer;
import net.apetheory.publicise.server.data.ResourceSet;

/**
 * Created by Christoph on 14.09.2014.
 */
public class JsonConverter {

    public String toJSON(ResourceSet value, String f) {
        //TODO handle value == null
        JSONSerializer serializer = new JSONSerializer();
        String[] fields = f.split(",");

        for(String field : fields) {
            serializer.include("objects." + field);
        }

        return serializer
                .exclude("objects.*")
                .exclude("*.class")
                .prettyPrint(true)
                .serialize(value);
    }

}
