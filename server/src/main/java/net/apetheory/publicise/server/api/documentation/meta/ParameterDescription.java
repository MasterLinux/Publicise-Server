package net.apetheory.publicise.server.api.documentation.meta;

import net.apetheory.publicise.server.api.documentation.ParameterType;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ParametersDescription.class)
public @interface ParameterDescription {
    boolean isRequired() default false;
    String description();
    ParameterType type();
    String returnType();
    String name();
}
