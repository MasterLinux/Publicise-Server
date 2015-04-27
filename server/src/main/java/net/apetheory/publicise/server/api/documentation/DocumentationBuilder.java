package net.apetheory.publicise.server.api.documentation;

import net.apetheory.publicise.server.api.documentation.command.*;

import javax.ws.rs.HttpMethod;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Christoph on 27.04.2015.
 */
public class DocumentationBuilder {
    private final Class clz;

    public DocumentationBuilder(Class clz) {
        this.clz = clz;
    }

    public ResourceDocumentation build() {
        ResourceDocumentation.Builder resourceDocumentationBuilder = new ResourceDocumentation.Builder();
        MethodDocumentation.Builder methodDocumentationBuilder;
        Set<String> allowedMethods = new HashSet<>();

        allowedMethods.add(HttpMethod.HEAD);
        allowedMethods.add(HttpMethod.OPTIONS);

        for(Method method : clz.getMethods()) {
            methodDocumentationBuilder = new MethodDocumentation.Builder();

            HttpPostCommand httpPostCommand;
            HttpGetMethodCommand httpGetCommand;
            HttpPutCommand httpPutCommand;
            HttpDeleteCommand httpDeleteCommand;

            QueryParameterDescriptionCommand queryParameterCommand;
            ErrorDescriptionCommand errorCommand;

            boolean isHttpMethod = false;

            for(Annotation annotation : method.getDeclaredAnnotations()) {
                queryParameterCommand = new QueryParameterDescriptionCommand(annotation, methodDocumentationBuilder);
                errorCommand = new ErrorDescriptionCommand(annotation, methodDocumentationBuilder);
                httpPostCommand = new HttpPostCommand(annotation, allowedMethods);
                httpGetCommand = new HttpGetMethodCommand(annotation, allowedMethods);
                httpPutCommand = new HttpPutCommand(annotation, allowedMethods);
                httpDeleteCommand = new HttpDeleteCommand(annotation, allowedMethods);

                if(httpDeleteCommand.execute()) {
                    methodDocumentationBuilder.setHttpMethod(HttpMethod.DELETE);
                    isHttpMethod = true;

                } else if(httpPutCommand.execute()) {
                    methodDocumentationBuilder.setHttpMethod(HttpMethod.PUT);
                    isHttpMethod = true;

                } else if(httpGetCommand.execute()) {
                    methodDocumentationBuilder.setHttpMethod(HttpMethod.GET);
                    isHttpMethod = true;

                } else if(httpPostCommand.execute()) {
                    methodDocumentationBuilder.setHttpMethod(HttpMethod.POST);
                    isHttpMethod = true;

                } else if(queryParameterCommand.execute()) {
                    continue;

                } else if(errorCommand.execute()) {
                    continue;
                }
            }

            if(isHttpMethod) {
                resourceDocumentationBuilder
                        .addMethod(methodDocumentationBuilder.build());
            }
        }

        return resourceDocumentationBuilder
                .setAllowedMethods(allowedMethods)
                .build();
    }
}
