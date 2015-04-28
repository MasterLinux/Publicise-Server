package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.MethodDocumentation;
import net.apetheory.publicise.server.api.documentation.meta.ParameterDescription;
import net.apetheory.publicise.server.api.documentation.meta.ParametersDescription;

import java.lang.annotation.Annotation;

/**
 * Created by Christoph on 27.04.2015.
 */
public class ParametersDescriptionCommand extends MethodDocumentationCommand<ParametersDescription> {

    public ParametersDescriptionCommand(Annotation annotation, MethodDocumentation.Builder builder) {
        super(annotation, builder);
    }

    @Override
    public void execute(ParametersDescription annotation, MethodDocumentation.Builder builder) {
        for(ParameterDescription parameter : annotation.value()) {
            new ParameterDescriptionCommand(parameter, builder).execute();
        }
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof ParametersDescription;
    }
}
