package net.apetheory.publicise.server.data.converter.exception;

/**
 * Created by cgrundmann on 31.07.15.
 */
public class ReadOnlyException extends IllegalAccessException {
    public ReadOnlyException(String fieldName) {
        super("Field " + fieldName + " is read only");
    }
}
