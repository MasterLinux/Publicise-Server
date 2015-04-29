package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.MethodDocumentation;
import net.apetheory.publicise.server.api.documentation.ParameterDocumentation;

import javax.ws.rs.HeaderParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 27.04.2015.
 */
public class HeaderDescriptionCommand extends MethodDocumentationCommand<HeaderParam> {

    private Parameter parameter;

    public HeaderDescriptionCommand(Annotation annotation, MethodDocumentation.Builder builder, Parameter parameter) {
        super(annotation, builder);
        this.parameter = parameter;
    }

    @Override
    public void execute(HeaderParam annotation, MethodDocumentation.Builder builder) {
        builder.addHeader(
                new ParameterDocumentation.Builder()
                        .setName(annotation.value())
                        .setType(parameter.getType().getName())
                        .build());
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof HeaderParam;
    }
}
