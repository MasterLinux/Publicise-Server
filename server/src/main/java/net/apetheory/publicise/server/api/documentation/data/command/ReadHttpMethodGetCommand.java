package net.apetheory.publicise.server.api.documentation.data.command;

import net.apetheory.publicise.server.api.documentation.data.model.EndpointModel;

import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Christoph on 02.05.2015.
 */
public class ReadHttpMethodGetCommand implements Command<Method, EndpointModel> {
    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof GET;
    }

    @Override
    public void execute(Method element, Annotation annotation, EndpointModel model) {
        model.setHttpMethod(HttpMethod.GET);
    }
}
