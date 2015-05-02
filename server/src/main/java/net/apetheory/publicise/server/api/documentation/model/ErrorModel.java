package net.apetheory.publicise.server.api.documentation.model;

import flexjson.JSON;

/**
 * Created by Christoph on 27.04.2015.
 */
public class ErrorModel {
    private int errorCode;
    private int statusCode;
    private String errorName;
    private String description;

    @JSON
    public int getErrorCode() {
        return errorCode;
    }

    @JSON
    public int getStatusCode() {
        return statusCode;
    }

    @JSON
    public String getErrorName() {
        return errorName;
    }

    @JSON
    public String getDescription() {
        return description;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
