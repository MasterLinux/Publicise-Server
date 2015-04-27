package net.apetheory.publicise.server.api.documentation.command;

import java.lang.annotation.Annotation;

/**
 * Created by Christoph on 27.04.2015.
 */
public abstract class DocumentationBuilderCommand<T extends Annotation> {
    private final Annotation annotation;

    public DocumentationBuilderCommand(Annotation annotation) {
        this.annotation = annotation;
    }

    public boolean isExpectedAnnotation() {
        return isExpectedAnnotation(annotation);
    }

    public boolean execute() {
        if(isExpectedAnnotation()) {
            execute((T) annotation);
            return true;
        }

        return false;
    }

    public abstract boolean isExpectedAnnotation(Annotation annotation);

    public abstract void execute(T annotation);
}
