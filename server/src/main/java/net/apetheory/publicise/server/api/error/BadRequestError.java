package net.apetheory.publicise.server.api.error;

/**
 * Created by Christoph on 13.06.2015.
 */
public class BadRequestError extends ApiError {

    @Override
    public String getErrorMessage() {
        return "Bad request";
    }

    @Override
    public int getStatusCode() {
        return 400;
    }

    @Override
    public int getErrorCode() {
        return 0x0300;
    }

    @Override
    public String getErrorName() {
        return "BAD_REQUEST_ERROR";
    }
}
