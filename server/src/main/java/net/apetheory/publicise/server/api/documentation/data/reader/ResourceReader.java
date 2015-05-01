package net.apetheory.publicise.server.api.documentation.data.reader;

import net.apetheory.publicise.server.api.documentation.data.command.ReadPathCommand;
import net.apetheory.publicise.server.api.documentation.data.model.EndpointModel;
import net.apetheory.publicise.server.api.documentation.data.model.ResourceModel;
import net.apetheory.publicise.server.data.utility.StringUtils;

import javax.ws.rs.HttpMethod;
import java.lang.reflect.Method;

/**
 * Created by Christoph on 01.05.2015.
 */
public class ResourceReader extends AnnotationReader<Class, ResourceModel> {

    public ResourceReader(Class annotatedClass) {
        super(annotatedClass, new ResourceModel());

        addCommand(new ReadPathCommand<>());
    }

    @Override
    public ResourceModel read() {
        ResourceModel resource = super.read();
        Class clz = getAnnotatedElement();
        EndpointReader endpointReader;

        // HEAD and OPTIONS method is allowed by default
        resource.addAllowedMethod(HttpMethod.HEAD);
        resource.addAllowedMethod(HttpMethod.OPTIONS);

        // get documentation for each API endpoint
        for(Method method : clz.getMethods()) {
            endpointReader = new EndpointReader(method);
            EndpointModel endpoint = endpointReader.read();

            // just add endpoints annotated with @POST, @GET, etc.
            if(!StringUtils.isNullOrEmpty(endpoint.getHttpMethod())) {
                resource.addEndpoint(endpoint);
            }
        }

        return resource;
    }
}
