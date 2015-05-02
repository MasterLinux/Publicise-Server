package net.apetheory.publicise.server.api.documentation.model;

import flexjson.JSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Christoph on 27.04.2015.
 */
public class ResourceModel implements PathModel, Descriptable {
    private List<ApiEndpointModel> endpoints = new ArrayList<>();
    private Set<String> allowedMethods = new HashSet<>();
    private String description;
    private String path;

    @JSON
    public List<ApiEndpointModel> getEndpoints() {
        return endpoints;
    }

    public void addEndpoint(ApiEndpointModel endpoint) {
        addAllowedMethod(endpoint.getHttpMethod());
        this.endpoints.add(endpoint);
    }

    @JSON
    public Set<String> getAllowedMethods() {
        return allowedMethods;
    }

    public void addAllowedMethod(String method) {
        allowedMethods.add(method);
    }

    @JSON
    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @JSON
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
