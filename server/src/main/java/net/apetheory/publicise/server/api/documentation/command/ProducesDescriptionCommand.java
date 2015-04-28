package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.MethodDocumentation;

import javax.ws.rs.Produces;
import java.lang.annotation.Annotation;

/**
 * Created by Christoph on 28.04.2015.
 */
public class ProducesDescriptionCommand extends DocumentationBuilderCommand<Produces> {
    private MethodDocumentation.Builder methodBuilder;

    public ProducesDescriptionCommand(Annotation annotation, MethodDocumentation.Builder builder) {
        super(annotation);
        methodBuilder = builder;
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof Produces;
    }

    @Override
    public void execute(Produces annotation) {
        methodBuilder.addProducesMediaTypes(annotation.value());
    }
}
