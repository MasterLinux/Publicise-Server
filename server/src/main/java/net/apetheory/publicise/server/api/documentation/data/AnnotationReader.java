package net.apetheory.publicise.server.api.documentation.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;

/**
 * Created by cgrundmann on 29.04.15.
 */
public class AnnotationReader<I, T extends Builder<I>> implements Reader<I> {
    private ArrayList<Command<T>> commands = new ArrayList<>();
    private AnnotatedElement element;
    private T builder;

    public AnnotationReader(AnnotatedElement element, T builder) {
        this.element = element;
        this.builder = builder;
    }

    public void add(Command<T> command) {
        commands.add(command);
    }

    @Override
    public I read() {
        for (Annotation annotation : element.getDeclaredAnnotations()) {
            for (Command<T> command : commands) {
                if (command.canExecute(annotation)) {
                    command.execute(element, annotation, builder);
                    break;
                }
            }
        }

        return builder.build();
    }
}
