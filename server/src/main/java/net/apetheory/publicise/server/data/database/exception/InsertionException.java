package net.apetheory.publicise.server.data.database.exception;

/**
 * An exception used when a data record could not be inserted.
 */
public class InsertionException extends Exception {

    public InsertionException(Exception e) {
        super("Unable to insert new data record", e);
    }
}
