package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.MethodDocumentation;
import net.apetheory.publicise.server.api.documentation.ParameterDocumentation;
import net.apetheory.publicise.server.api.documentation.meta.QueryParameterDescription;

import java.lang.annotation.Annotation;

/**
 * Created by Christoph on 27.04.2015.
 */
public class QueryParameterDescriptionCommand extends MethodDocumentationCommand<QueryParameterDescription> {

    public QueryParameterDescriptionCommand(Annotation annotation, MethodDocumentation.Builder builder) {
        super(annotation, builder);
    }

    @Override
    public void execute(QueryParameterDescription annotation, MethodDocumentation.Builder builder) {
        builder.addQueryParameter(
                new ParameterDocumentation.Builder()
                        .setDescription(annotation.description())
                        .setIsRequired(annotation.isRequired())
                        .setName(annotation.name())
                        .setType(annotation.type())
                        .build());
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof QueryParameterDescription;
    }
}
