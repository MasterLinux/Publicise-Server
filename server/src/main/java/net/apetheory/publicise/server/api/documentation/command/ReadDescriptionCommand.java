package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.meta.Description;
import net.apetheory.publicise.server.api.documentation.model.Descriptable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * Created by Christoph on 02.05.2015.
 */
public class ReadDescriptionCommand<TElement extends AnnotatedElement, TModel extends Descriptable> implements Command<TElement, TModel> {

    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof Description;
    }

    @Override
    public void execute(TElement element, Annotation annotation, TModel model) {
        model.setDescription(((Description) annotation).value());
    }
}
