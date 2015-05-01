package net.apetheory.publicise.server.api.documentation.data.model;

import java.util.*;

/**
 * Created by Christoph on 27.04.2015.
 */
public class EndpointModel implements PathModel {
    private String path;
    private String httpMethod;
    private String description;
    private List<ParameterModel> queryParameter = new ArrayList<>();
    private List<ParameterModel> pathParameter = new ArrayList<>();
    private List<ParameterModel> header = new ArrayList<>();
    private List<ErrorModel> errors = new ArrayList<>();
    private Set<String> produces = new HashSet<>();

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getDescription() {
        return description;
    }

    public List<ParameterModel> getQueryParameter() {
        return queryParameter;
    }

    public List<ParameterModel> getPathParameter() {
        return pathParameter;
    }

    public List<ParameterModel> getHeader() {
        return header;
    }

    public List<ErrorModel> getErrors() {
        return errors;
    }

    public String getPath() {
        return path;
    }

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

    public void setPath(String path) {
        this.path = path;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
