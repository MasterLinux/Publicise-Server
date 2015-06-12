package net.apetheory.publicise.server.api.error;

/**
 * Created by Christoph on 23.04.2015.
 */
public class DatabaseConnectionError extends DatabaseError { //TODO move to api namespace

    public static final String ERROR_MESSAGE = "Connection to database could not be established";
    public static final String ERROR_NAME = "ERROR_DB_CONNECTION_FAILED";

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
        return 0xCC00;
    }

    @Override
    public int getStatusCode() {
        return 503;
    }
}
