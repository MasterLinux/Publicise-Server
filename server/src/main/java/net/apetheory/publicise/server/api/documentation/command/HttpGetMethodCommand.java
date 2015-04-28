package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.MethodDocumentation;

import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created by Christoph on 27.04.2015.
 */
public class HttpGetMethodCommand extends HttpMethodCommand<GET> {

    public HttpGetMethodCommand(Annotation annotation, MethodDocumentation.Builder methodDocumentationBuilder, Set<String> allowedMethods) {
        super(annotation, methodDocumentationBuilder, allowedMethods);
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof GET;
    }

    @Override
    public void execute(GET annotation, MethodDocumentation.Builder builder, Set<String> allowedMethods) {
        builder.setHttpMethod(HttpMethod.GET);
        allowedMethods.add(HttpMethod.GET);
    }
}
