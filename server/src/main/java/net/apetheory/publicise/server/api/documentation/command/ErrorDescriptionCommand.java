package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.ErrorDocumentation;
import net.apetheory.publicise.server.api.documentation.MethodDocumentation;
import net.apetheory.publicise.server.api.documentation.meta.ErrorDescription;
import net.apetheory.publicise.server.data.ApiError;

import java.lang.annotation.Annotation;

/**
 * Created by Christoph on 27.04.2015.
 */
public class ErrorDescriptionCommand extends MethodDocumentationCommand<ErrorDescription> {

    public ErrorDescriptionCommand(Annotation annotation, MethodDocumentation.Builder builder) {
        super(annotation, builder);
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof ErrorDescription;
    }

    @Override
    public void execute(ErrorDescription annotation, MethodDocumentation.Builder builder) {
        try {
            for(Class errorClz : annotation.value()) {
                if (ApiError.class.isInstance(errorClz.newInstance())) {
                    builder.addError(
                            new ErrorDocumentation.Builder<>(errorClz).build()
                    );
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();   //TODO handle exception
        }
    }
}
