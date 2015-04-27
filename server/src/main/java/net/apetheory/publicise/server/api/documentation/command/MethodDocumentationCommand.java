package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.MethodDocumentation;

import java.lang.annotation.Annotation;

/**
 * Created by Christoph on 27.04.2015.
 */
public abstract class MethodDocumentationCommand<T extends Annotation> extends DocumentationBuilderCommand<T> {
    private final MethodDocumentation.Builder builder;

    public MethodDocumentationCommand(Annotation annotation, MethodDocumentation.Builder builder) {
        super(annotation);
        this.builder = builder;
    }

    @Override
    public void execute(T annotation) {
        execute(annotation, builder);
    }

    public abstract void execute(T annotation, MethodDocumentation.Builder builder);
}
