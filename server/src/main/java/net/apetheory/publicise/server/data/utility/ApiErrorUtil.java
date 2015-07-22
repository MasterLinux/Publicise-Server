package net.apetheory.publicise.server.data.utility;

import net.apetheory.publicise.server.api.error.*;
import net.apetheory.publicise.server.data.database.exception.ConnectionException;
import net.apetheory.publicise.server.data.database.exception.InsertionException;
import net.apetheory.publicise.server.data.database.exception.QueryException;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Christoph on 22.07.2015.
 */
public class ApiErrorUtil {

    @NotNull
    public static ApiError getApiError(Exception e) {
        ApiError error;

        if (e instanceof InsertionException) {
            error = new DatabaseInsertionError();
        } else if (e instanceof ConnectionException) {
            error = new DatabaseConnectionError();
        } else if(e instanceof QueryException) {
            error = new DatabaseQueryingError();
        } else {
            error = new InternalServerError();
        }

        return error;
    }
}
