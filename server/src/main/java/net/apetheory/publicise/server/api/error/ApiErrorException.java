package net.apetheory.publicise.server.api.error;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Representation of an exception used to throw ApiErrors
 */
public class ApiErrorException extends WebApplicationException {

    public ApiErrorException(ApiError error, boolean prettyPrint) {
        super(Response.status(error.getStatusCode())
                        .entity(error.toJson(prettyPrint))
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .build()
        );
    }
}
