package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.MethodDocumentation;

import javax.ws.rs.DELETE;
import javax.ws.rs.HttpMethod;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created by Christoph on 27.04.2015.
 */
public class HttpDeleteMethodCommand extends HttpMethodCommand<DELETE> {

    public HttpDeleteMethodCommand(Annotation annotation, MethodDocumentation.Builder methodDocumentationBuilder, Set<String> allowedMethods) {
        super(annotation, methodDocumentationBuilder, allowedMethods);
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof DELETE;
    }

    @Override
    public void execute(DELETE annotation, MethodDocumentation.Builder builder, Set<String> allowedMethods) {
        builder.setHttpMethod(HttpMethod.DELETE);
        allowedMethods.add(HttpMethod.DELETE);
    }
}
