package net.apetheory.publicise.server.api.documentation.reader;

import net.apetheory.publicise.server.api.documentation.command.*;
import net.apetheory.publicise.server.api.documentation.model.ApiEndpointModel;

import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 02.05.2015.
 */
public class ParameterReader extends AnnotationReader<Parameter, ApiEndpointModel> {

    public ParameterReader(Parameter annotatedElement, ApiEndpointModel apiEndpointModel) {
        super(annotatedElement, apiEndpointModel);

        addCommand(new ReadHeaderCommand());
        addCommand(new ReadQueryParamCommand());
        addCommand(new ReadPathParamCommand());
        addCommand(new ReadBeanParamCommand());
        addCommand(new ReadDescriptionCommand<>());
    }

}
