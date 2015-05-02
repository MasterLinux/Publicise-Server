package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.converter.TypeConverter;
import net.apetheory.publicise.server.api.documentation.model.ApiEndpointModel;
import net.apetheory.publicise.server.api.documentation.model.ParameterModel;

import javax.ws.rs.QueryParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 02.05.2015.
 */
public class ReadQueryParamCommand implements Command<Parameter, ApiEndpointModel> {
    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof QueryParam;
    }

    @Override
    public void execute(Parameter element, Annotation annotation, ApiEndpointModel model) {
        ParameterModel parameter = new ParameterModel();   //TODO add description & isRequired
        parameter.setName(((QueryParam)annotation).value());
        parameter.setType(TypeConverter.getJsonType(element));

        model.addQueryParameter(parameter);
    }
}
