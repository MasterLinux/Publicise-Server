package net.apetheory.publicise.server.api.documentation.command;

import javax.ws.rs.DELETE;
import javax.ws.rs.HttpMethod;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created by Christoph on 27.04.2015.
 */
public class HttpDeleteCommand extends HttpMethodCommand<DELETE> {

    public HttpDeleteCommand(Annotation annotation, Set<String> allowedMethods) {
        super(annotation, allowedMethods);
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof DELETE;
    }

    @Override
    public void execute(DELETE annotation, Set<String> allowedMethods) {
        allowedMethods.add(HttpMethod.DELETE);
    }
}
