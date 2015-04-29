package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.MethodDocumentation;
import net.apetheory.publicise.server.api.documentation.ParameterDocumentation;

import javax.ws.rs.PathParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 27.04.2015.
 */
public class PathParamDescriptionCommand extends MethodDocumentationCommand<PathParam> {

    private Parameter parameter;

    public PathParamDescriptionCommand(Annotation annotation, MethodDocumentation.Builder builder, Parameter parameter) {
        super(annotation, builder);
        this.parameter = parameter;
    }

    @Override
    public void execute(PathParam annotation, MethodDocumentation.Builder builder) {
        ParameterDocumentation p = new ParameterDocumentation.Builder()
                .setName(annotation.value())
                .setType(parameter.getType().getName())
                .build();

        builder.addPathParameter(p);
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof PathParam;
    }
}
