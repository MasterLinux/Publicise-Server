package net.apetheory.publicise.server.api.documentation;

import flexjson.JSON;

import java.util.*;

/**
 * Created by Christoph on 27.04.2015.
 */
public class MethodDocumentation {
    private String path;
    private String httpMethod;
    private String description;
    private List<ParameterDocumentation> queryParameter = new ArrayList<>();
    private List<ParameterDocumentation> pathParameter = new ArrayList<>();
    private List<ParameterDocumentation> header = new ArrayList<>();
    private List<ErrorDocumentation> errors = new ArrayList<>();
    private Set<String> produces = new HashSet<>();

    @JSON
    public String getHttpMethod() {
        return httpMethod;
    }

    @JSON
    public String getDescription() {
        return description;
    }

    @JSON
    public List<ParameterDocumentation> getQueryParameter() {
        return queryParameter;
    }

    @JSON
    public List<ParameterDocumentation> getPathParameter() {
        return pathParameter;
    }

    @JSON
    public List<ParameterDocumentation> getHeader() {
        return header;
    }

    @JSON
    public List<ErrorDocumentation> getErrors() {
        return errors;
    }

    @JSON
    public String getPath() {
        return path;
    }

    @JSON
    public Set<String> getProduces() {
        return produces;
    }

    static public class Builder {
        private MethodDocumentation documentation = new MethodDocumentation();

        public boolean isHttpMethod() {
            return documentation.httpMethod != null;
        }

        public Builder setHttpMethod(String httpMethod) {
            documentation.httpMethod = httpMethod;
            return this;
        }

        public Builder setDescription(String description) {
            documentation.description = description;
            return this;
        }

        public Builder addQueryParameter(ParameterDocumentation parameter) {
            documentation.queryParameter.add(parameter);
            return this;
        }

        public Builder addPathParameter(ParameterDocumentation parameter) {
            documentation.pathParameter.add(parameter);
            return this;
        }

        public Builder addHeader(ParameterDocumentation header) {
            documentation.header.add(header);
            return this;
        }

        public Builder addError(ErrorDocumentation error) {
            documentation.errors.add(error);
            return this;
        }

        public Builder setPath(String path) {
            documentation.path = path;
            return this;
        }

        public MethodDocumentation build() {
            return documentation;
        }

        public Builder addProducesMediaTypes(String[] produces) {
            Collections.addAll(documentation.produces, produces);
            return this;
        }
    }
}
