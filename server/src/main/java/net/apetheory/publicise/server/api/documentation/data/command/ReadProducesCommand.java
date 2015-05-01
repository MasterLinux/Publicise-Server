package net.apetheory.publicise.server.api.documentation.data.command;

import net.apetheory.publicise.server.api.documentation.data.model.EndpointModel;

import javax.ws.rs.Produces;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Christoph on 28.04.2015.
 */
public class ReadProducesCommand implements Command<Method, EndpointModel> {

    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof Produces;
    }

    @Override
    public void execute(Method element, Annotation annotation, EndpointModel model) {
        model.addProducesMediaTypes(((Produces)annotation).value());
    }
}
