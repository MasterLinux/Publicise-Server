package net.apetheory.publicise.server.api.documentation;

import net.apetheory.publicise.server.api.documentation.command.*;

import javax.ws.rs.HttpMethod;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Builder class which generates the
 * documentation of an API endpoint
 */
public class DocumentationBuilder {
    private final Class clz;

    public DocumentationBuilder(Class clz) {
        this.clz = clz;
    }

    public ResourceDocumentation build() {
        ResourceDocumentation.Builder resourceBuilder = new ResourceDocumentation.Builder();
        Set<String> allowedMethods = new HashSet<>();

        // HEAD and OPTIONS method is allowed by default
        allowedMethods.add(HttpMethod.HEAD);
        allowedMethods.add(HttpMethod.OPTIONS);

        for (Method method : clz.getMethods()) {
            MethodDocumentation.Builder methodBuilder = new MethodDocumentation.Builder();
            List<DocumentationBuilderCommand> commands;

            for (Annotation annotation : method.getDeclaredAnnotations()) {
                commands = new ArrayList<DocumentationBuilderCommand>() {{
                    add(new QueryParameterDescriptionCommand(annotation, methodBuilder));
                    add(new ErrorDescriptionCommand(annotation, methodBuilder));
                    add(new HttpPostMethodCommand(annotation, methodBuilder, allowedMethods));
                    add(new HttpGetMethodCommand(annotation, methodBuilder, allowedMethods));
                    add(new HttpPutMethodCommand(annotation, methodBuilder, allowedMethods));
                    add(new HttpDeleteMethodCommand(annotation, methodBuilder, allowedMethods));
                }};

                ListIterator<DocumentationBuilderCommand> it = commands.listIterator();
                DocumentationBuilderCommand command;

                while (it.hasNext()) {
                    command = it.next();

                    if (command.execute()) {
                        break;
                    }
                }
            }

            if (methodBuilder.isHttpMethod()) {
                resourceBuilder.addMethod(methodBuilder.build());
            }
        }

        return resourceBuilder
                .setAllowedMethods(allowedMethods)
                .build();
    }
}
