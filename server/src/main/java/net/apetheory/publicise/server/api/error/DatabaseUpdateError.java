package net.apetheory.publicise.server.api.error;

/**
 * Represents an internal server error which
 * is used whenever the insertion of a new
 * data record failed
 */
public class DatabaseUpdateError extends DatabaseError {

    public static final String ERROR_MESSAGE = "Unable to update resource";
    public static final String ERROR_NAME = "DB_UPDATE_FAILED_ERROR";

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
        return 0xCC01;
    }

    @Override
    public int getStatusCode() {
        return 500;
    }
}
