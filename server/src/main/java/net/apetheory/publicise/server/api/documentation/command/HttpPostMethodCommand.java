package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.MethodDocumentation;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created by Christoph on 27.04.2015.
 */
public class HttpPostMethodCommand extends HttpMethodCommand<POST> {

    public HttpPostMethodCommand(Annotation annotation, MethodDocumentation.Builder methodDocumentationBuilder, Set<String> allowedMethods) {
        super(annotation, methodDocumentationBuilder, allowedMethods);
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof POST;
    }

    @Override
    public void execute(POST annotation, MethodDocumentation.Builder builder, Set<String> allowedMethods) {
        builder.setHttpMethod(HttpMethod.POST);
        allowedMethods.add(HttpMethod.POST);
    }
}
