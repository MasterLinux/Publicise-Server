package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.MethodDocumentation;

import javax.ws.rs.BeanParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

/**
 * Created by Christoph on 27.04.2015.
 */
public class BeanParamDescriptionCommand extends MethodDocumentationCommand<BeanParam> {

    private Parameter parameter;

    public BeanParamDescriptionCommand(Annotation annotation, MethodDocumentation.Builder builder, Parameter parameter) {
        super(annotation, builder);
        this.parameter = parameter;
    }

    @Override
    public void execute(BeanParam annotation, MethodDocumentation.Builder builder) {
        Class beanParam = parameter.getType();

        for(Constructor constructor : beanParam.getConstructors()) {
            for(Parameter parameter : constructor.getParameters()) {
                for(Annotation paramAnnotation: parameter.getDeclaredAnnotations()) {
                    ArrayList<DocumentationBuilderCommand> commands = new ArrayList<DocumentationBuilderCommand>() {{
                        add(new HeaderDescriptionCommand(paramAnnotation, builder, parameter));
                        add(new QueryParamDescriptionCommand(paramAnnotation, builder, parameter));
                    }};

                    for (DocumentationBuilderCommand command : commands) {
                        if (command.execute()) {
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isExpectedAnnotation(Annotation annotation) {
        return annotation instanceof BeanParam;
    }
}
