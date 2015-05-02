package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.model.ApiEndpointModel;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.PUT;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Christoph on 02.05.2015.
 */
public class ReadHttpMethodPutCommand implements Command<Method, ApiEndpointModel> {
    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof PUT;
    }

    @Override
    public void execute(Method element, Annotation annotation, ApiEndpointModel model) {
        model.setHttpMethod(HttpMethod.PUT);
    }
}
