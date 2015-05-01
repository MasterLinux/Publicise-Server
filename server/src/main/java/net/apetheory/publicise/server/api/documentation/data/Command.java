package net.apetheory.publicise.server.api.documentation.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * Created by cgrundmann on 29.04.15.
 */
public interface Command<T> {
    boolean canExecute(Annotation annotation);
    void execute(AnnotatedElement element, Annotation annotation, T builder);
}
