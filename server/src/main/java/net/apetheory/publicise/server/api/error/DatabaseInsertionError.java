package net.apetheory.publicise.server.api.error;

/**
 * Represents an internal server error which
 * is used whenever the insertion of a new
 * data record failed
 */
public class DatabaseInsertionError extends DatabaseError {

    public static final String ERROR_MESSAGE = "Unable to insert new resource";
    public static final String ERROR_NAME = "DB_INSERTION_FAILED_ERROR";

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
