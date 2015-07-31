package net.apetheory.publicise.server.api.error;

/**
 * Represents an internal server error which
 * is used whenever the querying of a
 * data record failed
 */
public class DatabaseQueryingError extends DatabaseError {

    public static final String ERROR_MESSAGE = "Unable to get resource";
    public static final String ERROR_NAME = "DB_QUERYING_FAILED_ERROR";

    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    @Override
    public String getErrorName() {
        return ERROR_NAME;
    }

    @Override
    public int getErrorCode() {
        return 0xCC02;
    }

    @Override
    public int getStatusCode() {
        return 500;
    }
}