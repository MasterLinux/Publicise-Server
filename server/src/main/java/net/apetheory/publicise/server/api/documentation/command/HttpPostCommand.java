package net.apetheory.publicise.server.api.documentation.command;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created by Christoph on 27.04.2015.
 */
public class HttpPostCommand extends HttpMethodCommand<POST> {

    public HttpPostCommand(Annotation annotation, Set<String> allowedMethods) {
        super(annotation, allowedMethods);
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof POST;
    }

    @Override
    public void execute(POST annotation, Set<String> allowedMethods) {
        allowedMethods.add(HttpMethod.POST);
    }
}
