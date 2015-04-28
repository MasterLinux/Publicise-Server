package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.MethodDocumentation;
import net.apetheory.publicise.server.api.documentation.ResourceDocumentation;

import javax.ws.rs.Path;
import java.lang.annotation.Annotation;

/**
 * Created by Christoph on 28.04.2015.
 */
public class PathDescriptionCommand extends DocumentationBuilderCommand<Path> {
    private ResourceDocumentation.Builder resourceBuilder;
    private MethodDocumentation.Builder methodBuilder;

    public PathDescriptionCommand(Annotation annotation, ResourceDocumentation.Builder builder) {
        super(annotation);
        resourceBuilder = builder;
    }

    public PathDescriptionCommand(Annotation annotation, MethodDocumentation.Builder builder) {
        super(annotation);
        methodBuilder = builder;
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof Path;
    }

    @Override
    public void execute(Path annotation) {
        String path = annotation.value();

        if(resourceBuilder != null) {
            resourceBuilder.setPath(annotation.value());

        } else if(methodBuilder != null) {
            methodBuilder.setPath(path);
        }
    }
}
