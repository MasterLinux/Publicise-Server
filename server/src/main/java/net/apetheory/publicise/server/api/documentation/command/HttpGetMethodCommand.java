package net.apetheory.publicise.server.api.documentation.command;

import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created by Christoph on 27.04.2015.
 */
public class HttpGetMethodCommand extends HttpMethodCommand<GET> {

    public HttpGetMethodCommand(Annotation annotation, Set<String> allowedMethods) {
        super(annotation, allowedMethods);
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof GET;
    }

    @Override
    public void execute(GET annotation, Set<String> allowedMethods) {
        allowedMethods.add(HttpMethod.GET);
    }
}
