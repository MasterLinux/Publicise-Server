package net.apetheory.publicise.server.data;

import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;

/**
 * Representation of an error
 */
public abstract class ApiError {
    public static final int DefaultHttpStatusCode = 0;
    private int httpStatusCode;

    /**
     * Initializes the error with a HTTP status coce
     * @param httpStatusCode The HTTP status code which raises the error
     */
    public ApiError(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    /**
     * Initializes the error
     */
    public ApiError() {
        this.httpStatusCode = DefaultHttpStatusCode;
    }

    /**
     * Gets the message of the error
     * @return The error message
     */
    public abstract String getMessage();

    /**
     * Gets the HTTP status code which raises
     * the error or Error.DefaultHttpStatusCode
     * if the error isn't a HTTP error
     * @return The HTTP status code
     */
    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    /**
     * Gets the error code which represents a specific error
     * @return The error code
     */
    public abstract int getCode();

    /**
     * Converts the error to a BSON document
     * @return The BSON document which represents the error
     */
    public BsonDocument toDocument() {
        return new BsonDocument()
                .append("errorCode", new BsonInt32(getCode()))
                .append("errorMessage", new BsonString(getMessage()))
                .append("httpStatusCode", new BsonInt32(getHttpStatusCode()));
    }
}
