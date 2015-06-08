package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.meta.Required;
import net.apetheory.publicise.server.api.documentation.model.ParameterModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 02.05.2015.
 */
public class ReadRequiredCommand implements Command<Parameter, ParameterModel> {

    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof Required;
    }

    @Override
    public void execute(Parameter element, Annotation annotation, ParameterModel model) {
        model.setIsRequired(true);
    }
}
