package net.apetheory.publicise.server.api.documentation.command;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.PUT;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created by Christoph on 27.04.2015.
 */
public class HttpPutCommand extends HttpMethodCommand<PUT> {

    public HttpPutCommand(Annotation annotation, Set<String> allowedMethods) {
        super(annotation, allowedMethods);
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof PUT;
    }

    @Override
    public void execute(PUT annotation, Set<String> allowedMethods) {
        allowedMethods.add(HttpMethod.PUT);
    }
}
