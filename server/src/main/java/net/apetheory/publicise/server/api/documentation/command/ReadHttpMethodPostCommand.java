package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.model.EndpointModel;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Christoph on 02.05.2015.
 */
public class ReadHttpMethodPostCommand implements Command<Method, EndpointModel> {
    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof POST;
    }

    @Override
    public void execute(Method element, Annotation annotation, EndpointModel model) {
        model.setHttpMethod(HttpMethod.POST);
    }
}
