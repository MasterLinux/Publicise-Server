package net.apetheory.publicise.server.api.documentation.model;

import flexjson.JSON;

import java.util.*;

/**
 * Created by Christoph on 27.04.2015.
 */
public class ApiEndpointModel implements PathModel, Descriptable {
    private String path;
    private String httpMethod;
    private String description;
    private List<ParameterModel> queryParameter = new ArrayList<>();
    private List<ParameterModel> pathParameter = new ArrayList<>();
    private List<ParameterModel> header = new ArrayList<>();
    private List<ErrorModel> errors = new ArrayList<>();
    private Set<String> produces = new HashSet<>();

    @JSON
    public String getHttpMethod() {
        return httpMethod;
    }

    @JSON
    @Override
    public String getDescription() {
        return description;
    }

    @JSON
    public List<ParameterModel> getQueryParameter() {
        return queryParameter;
    }

    @JSON
    public List<ParameterModel> getPathParameter() {
        return pathParameter;
    }

    @JSON
    public List<ParameterModel> getHeader() {
        return header;
    }

    @JSON
    public List<ErrorModel> getErrors() {
        return errors;
    }

    @JSON
    @Override
    public String getPath() {
        return path;
    }

    @JSON
    public Set<String> getProduces() {
        return produces;
    }

    public void addQueryParameter(ParameterModel parameter) {
        queryParameter.add(parameter);
    }

    public void addPathParameter(ParameterModel parameter) {
        pathParameter.add(parameter);
    }

    public void addHeader(ParameterModel header) {
        this.header.add(header);
    }

    public void addError(ErrorModel error) {
        errors.add(error);
    }

    public void addProducesMediaTypes(String[] produces) {
        Collections.addAll(this.produces, produces);
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
