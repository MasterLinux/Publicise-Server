package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.model.ApiEndpointModel;

import javax.ws.rs.Produces;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Christoph on 28.04.2015.
 */
public class ReadProducesCommand implements Command<Method, ApiEndpointModel> {

    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof Produces;
    }

    @Override
    public void execute(Method element, Annotation annotation, ApiEndpointModel model) {
        model.addProducesMediaTypes(((Produces)annotation).value());
    }
}
