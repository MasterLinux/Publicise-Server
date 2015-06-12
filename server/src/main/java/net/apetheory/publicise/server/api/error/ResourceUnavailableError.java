package net.apetheory.publicise.server.api.error;

/**
 * Created by Christoph on 03.05.2015.
 */
public class ResourceUnavailableError extends ApiError {

    @Override
    public String getErrorMessage() {
        return "The requested resource is currently unavailable";
    }

    @Override
    public int getStatusCode() {
        return 500;
    }

    @Override
    public int getErrorCode() {
        return 0x2200;
    }

    @Override
    public String getErrorName() {
        return "ERROR_RESOURCE_UNAVAILABLE";
    }
}
