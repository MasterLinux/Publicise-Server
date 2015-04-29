package net.apetheory.publicise.server.api.documentation;

import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 29.04.2015.
 */
public class TypeConverter {

    public static final String TYPE_STRING = "string";

    static public String getJsonType(Parameter parameter) {
        String paramType = parameter.getType().getTypeName();
        String jsonType = null;

        if(String.class.getTypeName().equals(paramType)) {
            jsonType = TYPE_STRING;
        }

        return jsonType;
    }
}
