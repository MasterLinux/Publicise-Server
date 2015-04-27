package net.apetheory.publicise.server.api.documentation;

import flexjson.JSON;
import net.apetheory.publicise.server.data.ApiError;

/**
 * Created by Christoph on 27.04.2015.
 */
public class ErrorDocumentation {
    private int errorCode;
    private int statusCode;
    private String errorName;
    private String description;

    @JSON
    public int getErrorCode() {
        return errorCode;
    }

    @JSON
    public int getStatusCode() {
        return statusCode;
    }

    @JSON
    public String getErrorName() {
        return errorName;
    }

    @JSON
    public String getDescription() {
        return description;
    }

    static public class Builder<T extends ApiError> {
        private ErrorDocumentation documentation = new ErrorDocumentation();

        public Builder(Class<T> errorType) {
            ApiError error = null;
            try {
                error = errorType.newInstance();

                setErrorCode(error.getErrorCode());
                setStatusCode(error.getStatusCode());
                setErrorName(error.getErrorName());
                setDescription(error.getErrorMessage());

            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace(); //TODO handle exception
            }
        }

        private Builder setErrorCode(int code) {
            documentation.errorCode = code;
            return this;
        }

        private Builder setStatusCode(int code) {
            documentation.statusCode = code;
            return this;
        }

        private Builder setErrorName(String name) {
            documentation.errorName = name;
            return this;
        }

        private Builder setDescription(String description) {
            documentation.description = description;
            return this;
        }

        public ErrorDocumentation build() {
            return documentation;
        }
    }
}
