package net.apetheory.publicise.server.api.error;

/**
 * Created by Christoph on 13.06.2015.
 */
public class InternalServerError extends ApiError {

    @Override
    public String getErrorMessage() {
        return "Internal server error";
    }

    @Override
    public int getStatusCode() {
        return 500;
    }

    @Override
    public int getErrorCode() {
        return 0x0200;
    }

    @Override
    public String getErrorName() {
        return "INTERNAL_SERVER_ERROR";
    }
}
