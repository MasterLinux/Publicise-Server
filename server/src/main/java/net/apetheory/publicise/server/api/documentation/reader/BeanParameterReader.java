package net.apetheory.publicise.server.api.documentation.reader;

import net.apetheory.publicise.server.api.documentation.command.Command;
import net.apetheory.publicise.server.api.documentation.model.ApiEndpointModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 02.05.2015.
 */
public class BeanParameterReader extends ParameterReader {

    public BeanParameterReader(Parameter parameter, ApiEndpointModel model) {
        super(parameter, model);
    }

    @Override
    public ApiEndpointModel read() {
        Class beanParam = getAnnotatedElement().getType();
        ApiEndpointModel model = getModel();

        for(Constructor constructor : beanParam.getConstructors()) {
            for(Parameter parameter : constructor.getParameters()) {
                for(Annotation annotation: parameter.getDeclaredAnnotations()) {
                    for (Command<Parameter, ApiEndpointModel> command : getCommands()) {

                        if (command.canExecute(annotation)) {
                            command.execute(parameter, annotation, model);
                        }
                    }
                }
            }
        }

        return model;
    }
}
