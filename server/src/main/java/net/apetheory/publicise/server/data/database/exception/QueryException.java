package net.apetheory.publicise.server.data.database.exception;

/**
 * An exception used when a query could not be solved.
 */
public class QueryException extends Exception {

    public QueryException(Throwable t) {
        super("Unable to get requested data records", t);
    }
}
