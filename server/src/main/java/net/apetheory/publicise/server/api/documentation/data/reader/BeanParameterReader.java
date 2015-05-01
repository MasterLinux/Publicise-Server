package net.apetheory.publicise.server.api.documentation.data.reader;

import net.apetheory.publicise.server.api.documentation.data.command.Command;
import net.apetheory.publicise.server.api.documentation.data.model.EndpointModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 02.05.2015.
 */
public class BeanParameterReader extends ParameterReader {

    public BeanParameterReader(Parameter parameter, EndpointModel model) {
        super(parameter, model);
    }

    @Override
    public EndpointModel read() {
        Class beanParam = getAnnotatedElement().getType();
        EndpointModel model = getModel();

        for(Constructor constructor : beanParam.getConstructors()) {
            for(Parameter parameter : constructor.getParameters()) {
                for(Annotation annotation: parameter.getDeclaredAnnotations()) {
                    for (Command<Parameter, EndpointModel> command : getCommands()) {

                        if (command.canExecute(annotation)) {
                            command.execute(parameter, annotation, model);
                            break;
                        }
                    }
                }
            }
        }

        return model;
    }
}
