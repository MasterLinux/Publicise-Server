package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.model.EndpointModel;
import net.apetheory.publicise.server.api.documentation.model.ParameterModel;

import javax.ws.rs.PathParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 02.05.2015.
 */
public class ReadPathParamCommand implements Command<Parameter, EndpointModel> {
    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof PathParam;
    }

    @Override
    public void execute(Parameter element, Annotation annotation, EndpointModel model) {
        ParameterModel parameter = new ParameterModel();   //TODO add description & isRequired
        parameter.setName(((PathParam)annotation).value());
        parameter.setType(element.getType().getName());

        model.addPathParameter(parameter);
    }
}
