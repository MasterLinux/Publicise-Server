package net.apetheory.publicise.server.api.documentation.data.command;

import net.apetheory.publicise.server.api.documentation.data.model.PathModel;

import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * Created by Christoph on 28.04.2015.
 */
public class ReadPathCommand<TElement extends AnnotatedElement, TModel extends PathModel> implements Command<TElement, TModel> {

    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof Path;
    }

    @Override
    public void execute(TElement element, Annotation annotation, TModel model) {
        model.setPath(((Path)annotation).value());
    }
}
