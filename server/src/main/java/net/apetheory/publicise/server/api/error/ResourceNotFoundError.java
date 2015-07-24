package net.apetheory.publicise.server.api.error;

/**
 * Created by Christoph on 13.06.2015.
 */
public class ResourceNotFoundError extends ApiError {

    @Override
    public String getErrorMessage() {
        return "Resource not found";
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

    @Override
    public int getErrorCode() {
        return 0x0300;
    }

    @Override
    public String getErrorName() {
        return "RESOURCE_NOT_FOUND_ERROR";
    }
}
