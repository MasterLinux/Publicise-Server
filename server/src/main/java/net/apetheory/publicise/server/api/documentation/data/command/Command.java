package net.apetheory.publicise.server.api.documentation.data.command;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * Created by cgrundmann on 29.04.15.
 */
public interface Command<TElement extends AnnotatedElement, TModel> {
    boolean canExecute(Annotation annotation);
    void execute(TElement element, Annotation annotation, TModel model);
}
