package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.model.EndpointModel;
import net.apetheory.publicise.server.api.documentation.reader.BeanParameterReader;

import javax.ws.rs.BeanParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 02.05.2015.
 */
public class ReadBeanParamCommand implements Command<Parameter, EndpointModel> {

    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof BeanParam;
    }

    @Override
    public void execute(Parameter element, Annotation annotation, EndpointModel model) {
        new BeanParameterReader(element, model).read();
    }
}