package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.converter.TypeConverter;
import net.apetheory.publicise.server.api.documentation.model.ParameterModel;

import javax.ws.rs.PathParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 02.05.2015.
 */
public class ReadPathParamCommand implements Command<Parameter, ParameterModel> {
    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof PathParam;
    }

    @Override
    public void execute(Parameter element, Annotation annotation, ParameterModel model) {
        model.setName(((PathParam) annotation).value());
        model.setType(TypeConverter.getJsonType(element));
        model.setParameterType(ParameterModel.ParameterType.Path);
    }
}
