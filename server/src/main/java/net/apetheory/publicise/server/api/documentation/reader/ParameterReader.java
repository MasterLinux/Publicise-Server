package net.apetheory.publicise.server.api.documentation.reader;

import net.apetheory.publicise.server.api.documentation.command.ReadBeanParamCommand;
import net.apetheory.publicise.server.api.documentation.command.ReadHeaderCommand;
import net.apetheory.publicise.server.api.documentation.command.ReadPathParamCommand;
import net.apetheory.publicise.server.api.documentation.command.ReadQueryParamCommand;
import net.apetheory.publicise.server.api.documentation.model.EndpointModel;

import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 02.05.2015.
 */
public class ParameterReader extends AnnotationReader<Parameter, EndpointModel> {

    public ParameterReader(Parameter annotatedElement, EndpointModel endpointModel) {
        super(annotatedElement, endpointModel);

        addCommand(new ReadHeaderCommand());
        addCommand(new ReadQueryParamCommand());
        addCommand(new ReadPathParamCommand());
        addCommand(new ReadBeanParamCommand());
    }

}
