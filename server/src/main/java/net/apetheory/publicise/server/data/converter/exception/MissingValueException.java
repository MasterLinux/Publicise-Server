package net.apetheory.publicise.server.data.converter.exception;

/**
 * Created by cgrundmann on 31.07.15.
 */
public class MissingValueException extends Exception {
    public MissingValueException() {
        super("Value is missing");
    }
}
