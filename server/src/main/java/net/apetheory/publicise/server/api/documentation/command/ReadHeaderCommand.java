package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.model.EndpointModel;
import net.apetheory.publicise.server.api.documentation.model.ParameterModel;

import javax.ws.rs.HeaderParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 02.05.2015.
 */
public class ReadHeaderCommand implements Command<Parameter, EndpointModel> {

    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof HeaderParam;
    }

    @Override
    public void execute(Parameter element, Annotation annotation, EndpointModel model) {
        ParameterModel parameter = new ParameterModel();   //TODO add description & isRequired
        parameter.setName(((HeaderParam)annotation).value());
        parameter.setType(element.getType().getName());

        model.addHeader(parameter);
    }
}