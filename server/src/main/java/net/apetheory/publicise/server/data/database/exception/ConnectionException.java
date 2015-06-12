package net.apetheory.publicise.server.data.database.exception;

/**
 * An exception used when the connection
 * to the database could not be established.
 */
public class ConnectionException extends Exception {

    public ConnectionException(Exception e) {
        super("Connection to database could not be established", e);
    }
}
