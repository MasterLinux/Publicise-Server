package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.model.ParameterModel;

import javax.ws.rs.DefaultValue;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 02.05.2015.
 */
public class ReadDefaultValueCommand implements Command<Parameter, ParameterModel> {

    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof DefaultValue;
    }

    @Override
    public void execute(Parameter element, Annotation annotation, ParameterModel model) {
        model.setDefaultValue(((DefaultValue) annotation).value());
    }
}
