package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.MethodDocumentation;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created by Christoph on 27.04.2015.
 */
public abstract class HttpMethodCommand<T extends Annotation> extends DocumentationBuilderCommand<T> {
    private final MethodDocumentation.Builder builder;
    private final Set<String> allowedMethods;

    public HttpMethodCommand(Annotation annotation, MethodDocumentation.Builder builder, Set<String> allowedMethods) {
        super(annotation);
        this.builder = builder;
        this.allowedMethods = allowedMethods;
    }

    @Override
    public void execute(T annotation) {
       execute(annotation, builder, allowedMethods);
    }

    public abstract void execute(T annotation, MethodDocumentation.Builder builder, Set<String> allowedMethods);
}
