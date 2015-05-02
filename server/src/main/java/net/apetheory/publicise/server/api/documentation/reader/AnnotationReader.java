package net.apetheory.publicise.server.api.documentation.reader;

import net.apetheory.publicise.server.api.documentation.command.Command;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;

/**
 * Created by cgrundmann on 29.04.15.
 */
public class AnnotationReader<TElement extends AnnotatedElement, TModel> implements Reader<TModel> {
    private ArrayList<Command<TElement, TModel>> commands = new ArrayList<>();
    private TElement annotatedElement;
    private TModel model;

    public AnnotationReader(TElement annotatedElement, TModel model) {
        this.annotatedElement = annotatedElement;
        this.model = model;
    }

    public void addCommand(Command<TElement, TModel> command) {
        commands.add(command);
    }

    public TElement getAnnotatedElement() {
        return annotatedElement;
    }

    public TModel getModel() {
        return model;
    }

    public ArrayList<Command<TElement, TModel>> getCommands() {
        return commands;
    }

    @Override
    public TModel read() {
        for (Annotation annotation : annotatedElement.getDeclaredAnnotations()) {
            for (Command<TElement, TModel> command : commands) {
                if (command.canExecute(annotation)) {
                    command.execute(annotatedElement, annotation, model);
                    break;
                }
            }
        }

        return model;
    }
}
