package net.apetheory.publicise.server.api.documentation.reader;

import net.apetheory.publicise.server.api.documentation.command.*;
import net.apetheory.publicise.server.api.documentation.model.ApiEndpointModel;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 01.05.2015.
 */
public class ApiEndpointReader extends AnnotationReader<Method, ApiEndpointModel> {

    public ApiEndpointReader(Method annotatedMethod) {
        super(annotatedMethod, new ApiEndpointModel());

        addCommand(new ReadPathCommand<>());
        addCommand(new ReadProducesCommand());
        addCommand(new ReadErrorsCommand());
        addCommand(new ReadHttpMethodGetCommand());
        addCommand(new ReadHttpMethodPutCommand());
        addCommand(new ReadHttpMethodPostCommand());
        addCommand(new ReadHttpMethodDeleteCommand());
        addCommand(new ReadDescriptionCommand<>());
    }

    @Override
    public ApiEndpointModel read() {
        ApiEndpointModel endpoint = super.read();
        Method method = getAnnotatedElement();
        ParameterReader parameterReader;

        // get each parameter and header of this endpoint
        for(Parameter parameter : method.getParameters()) {
            parameterReader = new ParameterReader(parameter, endpoint);
            parameterReader.read();
        }

        return endpoint;
    }
}
