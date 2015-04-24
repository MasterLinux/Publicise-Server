package net.apetheory.publicise.server.data.database.error;

/**
 * Created by Christoph on 23.04.2015.
 */
public class ConnectionError extends DatabaseError {
    @Override
    public String getMessage() {
        return "Connection to database could not be established";
    }

    @Override
    public int getCode() {
        return 0;
    }
}
