package net.apetheory.publicise.server.api.documentation.command;

import net.apetheory.publicise.server.api.documentation.model.ApiEndpointModel;
import net.apetheory.publicise.server.api.documentation.model.ErrorModel;
import net.apetheory.publicise.server.api.documentation.meta.Errors;
import net.apetheory.publicise.server.api.error.ApiError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Christoph on 01.05.2015.
 */
public class ReadErrorsCommand implements Command<Method, ApiEndpointModel> {

    @Override
    public boolean canExecute(Annotation annotation) {
        return annotation instanceof Errors;
    }

    @Override
    public void execute(Method element, Annotation annotation, ApiEndpointModel apiEndpointModel) {
        try {
            for (Class errorClass : ((Errors) annotation).value()) {
                ErrorModel errorModel;

                if (ApiError.class.isInstance(errorClass.newInstance())
                        && (errorModel = createError(errorClass)) != null) {
                    apiEndpointModel.addError(errorModel);
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();   //TODO handle exception
        }
    }

    /**
     * Creates a error model with the help of the error class
     * @param errorClass The class of the error
     * @return The error model or null on error
     */
    private ErrorModel createError(Class<ApiError> errorClass) {
        ErrorModel model = new ErrorModel();

        try {
            ApiError error = errorClass.newInstance();

            model.setErrorCode(error.getErrorCode());
            model.setStatusCode(error.getStatusCode());
            model.setErrorName(error.getErrorName());
            model.setDescription(error.getErrorMessage());

        } catch (InstantiationException | IllegalAccessException e) {
            model = null;
        }

        return model;
    }
}
