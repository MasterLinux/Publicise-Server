package net.apetheory.publicise.server.data.database.exception;

/**
 * Created by Christoph on 24.07.2015.
 */
public class NotFoundException extends Exception {
    public NotFoundException(String id) {
        super("Resource with ID <" + id + "> not found");
    }
}
