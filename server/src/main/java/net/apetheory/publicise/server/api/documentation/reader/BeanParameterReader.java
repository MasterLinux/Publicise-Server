package net.apetheory.publicise.server.api.documentation.reader;

import net.apetheory.publicise.server.api.documentation.command.Command;
import net.apetheory.publicise.server.api.documentation.model.ParameterModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 02.05.2015.
 */
public class BeanParameterReader extends ParameterReader {

    public BeanParameterReader(Parameter parameter) {
        super(parameter);
    }

    public BeanParameterReader(Parameter parameter, ParameterModel model) {
        super(parameter, model);
    }

    @Override
    public ParameterModel read() {
        Class beanParam = getAnnotatedElement().getType();
        ParameterModel model = getModel();

        for(Constructor constructor : beanParam.getConstructors()) {
            for(Parameter parameter : constructor.getParameters()) {
                for(Annotation annotation: parameter.getDeclaredAnnotations()) {
                    for (Command<Parameter, ParameterModel> command : getCommands()) {

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
