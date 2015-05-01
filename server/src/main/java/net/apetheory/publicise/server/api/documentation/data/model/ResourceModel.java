package net.apetheory.publicise.server.api.documentation.data.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Christoph on 27.04.2015.
 */
public class ResourceModel implements PathModel {
    private List<EndpointModel> endpoints = new ArrayList<>();
    private Set<String> allowedMethods = new HashSet<>();
    private String path;

    public List<EndpointModel> getEndpoints() {
        return endpoints;
    }

    public void addEndpoint(EndpointModel endpoint) {
        addAllowedMethod(endpoint.getHttpMethod());
        this.endpoints.add(endpoint);
    }

    public Set<String> getAllowedMethods() {
        return allowedMethods;
    }

    public void addAllowedMethod(String method) {
        allowedMethods.add(method);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
