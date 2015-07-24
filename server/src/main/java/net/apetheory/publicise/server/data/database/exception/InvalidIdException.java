package net.apetheory.publicise.server.data.database.exception;

/**
 * Created by Christoph on 24.07.2015.
 */
public class InvalidIdException extends Exception {

    public InvalidIdException(String id) {
        super("Given ID <" + id + "> is invalid");
    }

}
