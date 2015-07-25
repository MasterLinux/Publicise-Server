package net.apetheory.publicise.server.data.database.exception;

/**
 * Created by Christoph on 25.07.2015.
 */
public class UpdateException extends Exception {

    public UpdateException(Throwable t) {
        super("Unable to update resource", t);
    }
}
