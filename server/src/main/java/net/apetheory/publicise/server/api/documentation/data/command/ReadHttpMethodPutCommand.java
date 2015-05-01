package net.apetheory.publicise.server.api.documentation.data.command;

import net.apetheory.publicise.server.api.documentation.data.model.EndpointModel;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.PUT;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Christoph on 02.05.2015.
 */
public class ReadHttpMethodPutCommand implements Command<Method, EndpointModel> {
    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof PUT;
    }

    @Override
    public void execute(Method element, Annotation annotation, EndpointModel model) {
        model.setHttpMethod(HttpMethod.PUT);
    }
}
