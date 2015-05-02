package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.model.ApiEndpointModel;

import javax.ws.rs.Consumes;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Christoph on 28.04.2015.
 */
public class ReadConsumesCommand implements Command<Method, ApiEndpointModel> {

    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof Consumes;
    }

    @Override
    public void execute(Method element, Annotation annotation, ApiEndpointModel model) {
        model.addConsumesMediaTypes(((Consumes)annotation).value());
    }
}
