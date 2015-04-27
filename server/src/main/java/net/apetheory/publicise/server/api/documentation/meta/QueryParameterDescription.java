package net.apetheory.publicise.server.api.documentation.meta;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface QueryParameterDescription {
    boolean isRequired() default false;
    String description();
    String name();
    String type();
}
