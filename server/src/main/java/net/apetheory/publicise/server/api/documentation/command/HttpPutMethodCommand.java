package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.MethodDocumentation;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.PUT;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created by Christoph on 27.04.2015.
 */
public class HttpPutMethodCommand extends HttpMethodCommand<PUT> {

    public HttpPutMethodCommand(Annotation annotation, MethodDocumentation.Builder methodDocumentationBuilder, Set<String> allowedMethods) {
        super(annotation, methodDocumentationBuilder, allowedMethods);
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof PUT;
    }

    @Override
    public void execute(PUT annotation, MethodDocumentation.Builder builder, Set<String> allowedMethods) {
        builder.setHttpMethod(HttpMethod.PUT);
        allowedMethods.add(HttpMethod.PUT);
    }
}
