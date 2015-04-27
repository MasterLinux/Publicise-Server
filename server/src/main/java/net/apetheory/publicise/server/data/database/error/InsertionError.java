package net.apetheory.publicise.server.data.database.error;

/**
 * Represents an internal server error which
 * is used whenever the insertion of a new
 * data record failed
 */
public class InsertionError extends DatabaseError {

    public static final String ERROR_MESSAGE = "Unable to insert new data record";
    public static final String ERROR_NAME = "ERROR_DB_INSERTION_FAILED";

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
