package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.MethodDocumentation;
import net.apetheory.publicise.server.api.documentation.ParameterDocumentation;
import net.apetheory.publicise.server.api.documentation.meta.ParameterDescription;

import java.lang.annotation.Annotation;

/**
 * Created by Christoph on 27.04.2015.
 */
public class ParameterDescriptionCommand extends MethodDocumentationCommand<ParameterDescription> {

    public ParameterDescriptionCommand(Annotation annotation, MethodDocumentation.Builder builder) {
        super(annotation, builder);
    }

    @Override
    public void execute(ParameterDescription annotation, MethodDocumentation.Builder builder) {
        ParameterDocumentation parameter = new ParameterDocumentation.Builder()
                .setDescription(annotation.description())
                .setIsRequired(annotation.isRequired())
                .setName(annotation.name())
                .setType(annotation.returnType())
                .build();

        switch (annotation.type()) {
            case Path:
                builder.addPathParameter(parameter);
                break;
            case Query:
                builder.addQueryParameter(parameter);
                break;
        }
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof ParameterDescription;
    }
}
