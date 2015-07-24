package net.apetheory.publicise.server.data.database.exception;

/**
 * Created by Christoph on 24.07.2015.
 */
public class DeleteException extends Exception {

    public DeleteException(Throwable t) {
        super("Unable to delete data record", t);
    }
}
