package net.apetheory.publicise.server.data.converter;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.resource.BaseResource;
import net.apetheory.publicise.server.resource.MetaModel;

import java.util.Collections;
import java.util.HashSet;

//TODO allow filtering of "field.sub_field"
public class FieldsParameterExclusionStrategy implements ExclusionStrategy {
    HashSet<String> allowedFields;

    public FieldsParameterExclusionStrategy(String fields) {
        allowedFields = new HashSet<>();

        if(fields != null) {
            Collections.addAll(allowedFields, fields.split(","));
        }
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        boolean skipField = false;

        if(allowedFields.size() > 0) {
            skipField = true;

            if (f.getDeclaringClass().equals(ResourceSet.class) ||
                    f.getDeclaringClass().equals(MetaModel.class)) {
                skipField = false;
            } else if (isResource(f.getDeclaringClass())) {
                if (allowedFields.contains(f.getName())) {
                    skipField = false;
                }
            }
        }

        return skipField;
    }

    private boolean isResource(Class<?> clz) {
        boolean isRes;

        if(!clz.equals(BaseResource.class) && clz.getSuperclass() != null) {
            isRes = isResource(clz.getSuperclass());
        } else {
            isRes = true;
        }

        return isRes;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
