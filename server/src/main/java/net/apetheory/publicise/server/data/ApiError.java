package net.apetheory.publicise.server.data;

import flexjson.JSON;
import flexjson.JSONSerializer;

/**
 * Representation of an API error
 */
public abstract class ApiError {

    /**
     * Gets the message of the error
     * @return The error message
     */
    @JSON
    public abstract String getErrorMessage();

    /**
     * Gets the HTTP status code which raises the error
     * @return The HTTP status code
     */
    @JSON
    public abstract int getStatusCode();

    /**
     * Gets the error code which represents a specific error
     * @return The error code
     */
    @JSON
    public abstract int getErrorCode();

    /**
     * Gets the name of the error
     * @return The name of the error
     */
    @JSON
    public abstract String getErrorName();

    /**
     * Converts the error to its JSON representation
     * @return The error as JSON formatted string
     */
    public String toJson() {
        return toJson(true);
    }

    public String toJson(boolean prettyPrint) {
        return new JSONSerializer()
                .prettyPrint(prettyPrint)
                .exclude("*.class")
                .serialize(this);
    }
}
