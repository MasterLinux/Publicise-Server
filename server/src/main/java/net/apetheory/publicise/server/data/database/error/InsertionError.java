package net.apetheory.publicise.server.data.database.error;

/**
 * Created by Christoph on 23.04.2015.
 */
public class InsertionError extends DatabaseError {
    @Override
    public String getMessage() {
        return "Insertion failed";
    }

    @Override
    public int getCode() {
        return 1;
    }
}
