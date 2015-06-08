package net.apetheory.publicise.server.api.documentation.converter;

import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 29.04.2015.
 */
public class TypeConverter {

    public static final String JSON_TYPE_STRING = "string";
    public static final String JSON_TYPE_OBJECT = "object";
    public static final String JSON_TYPE_ARRAY = "array";
    public static final String JSON_TYPE_NUMBER = "number";
    public static final String JSON_TYPE_BOOLEAN = "boolean";

    static public String getJsonType(Parameter parameter) {
        String paramType = parameter.getType().getTypeName();
        String jsonType = null;

        if(String.class.getName().equals(paramType)) {
            jsonType = JSON_TYPE_STRING;
        } else if(boolean.class.getName().equals(paramType)) {
            jsonType = JSON_TYPE_BOOLEAN;
        } else if(int.class.getName().equals(paramType) || long.class.getName().equals(paramType)) {
            jsonType = JSON_TYPE_NUMBER;
        }

        return jsonType;
    }
}
