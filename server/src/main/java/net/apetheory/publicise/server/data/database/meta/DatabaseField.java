package net.apetheory.publicise.server.data.database.meta;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Christoph on 13.09.2014.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseField {
    Visibility visibility() default Visibility.VISIBLE;

    boolean isFinal() default false;

    boolean isRequired() default true;
}
