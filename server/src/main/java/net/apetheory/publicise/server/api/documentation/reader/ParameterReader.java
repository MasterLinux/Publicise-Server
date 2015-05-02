package net.apetheory.publicise.server.api.documentation.reader;

import net.apetheory.publicise.server.api.documentation.command.*;
import net.apetheory.publicise.server.api.documentation.model.ParameterModel;

import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 02.05.2015.
 */
public class ParameterReader extends AnnotationReader<Parameter, ParameterModel> {

    public ParameterReader(Parameter annotatedElement, ParameterModel model) {
        super(annotatedElement, model);

        addCommand(new ReadHeaderCommand());
        addCommand(new ReadQueryParamCommand());
        addCommand(new ReadPathParamCommand());
        addCommand(new ReadBeanParamCommand());
        addCommand(new ReadDescriptionCommand<>());
        addCommand(new ReadRequiredCommand());
        addCommand(new ReadDefaultValueCommand());
    }

    public ParameterReader(Parameter annotatedElement) {
        this(annotatedElement, new ParameterModel());
    }

}
