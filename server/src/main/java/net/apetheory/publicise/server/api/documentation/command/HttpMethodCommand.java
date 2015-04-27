package net.apetheory.publicise.server.api.documentation.command;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created by Christoph on 27.04.2015.
 */
public abstract class HttpMethodCommand<T extends Annotation> extends DocumentationBuilderCommand<T> {
    private final Set<String> allowedMethods;

    public HttpMethodCommand(Annotation annotation, Set<String> allowedMethods) {
        super(annotation);
        this.allowedMethods = allowedMethods;
    }

    @Override
    public void execute(T annotation) {
       execute(annotation, allowedMethods);
    }

    public abstract void execute(T annotation, Set<String> allowedMethods);
}
