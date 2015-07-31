package net.apetheory.publicise.server.api.error;

/**
 *
 */
public class DatabaseDeletionError extends DatabaseError {

    public static final String ERROR_MESSAGE = "Unable to delete resource";
    public static final String ERROR_NAME = "DB_DELETION_FAILED_ERROR";

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