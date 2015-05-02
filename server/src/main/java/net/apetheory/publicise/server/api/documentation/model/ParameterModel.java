package net.apetheory.publicise.server.api.documentation.model;

import flexjson.JSON;

/**
 * Created by Christoph on 27.04.2015.
 */
public class ParameterModel implements Descriptable {
    private String name;
    private String type;
    private String description;
    private boolean isRequired;
    private ParameterType parameterType = ParameterType.Unknown;
    private String defaultValue;

    @JSON
    public String getName() {
        return name;
    }

    @JSON
    public String getType() {
        return type;
    }

    @JSON
    @Override
    public String getDescription() {
        return description;
    }

    @JSON
    public boolean getIsRequired() {
        return isRequired;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    @JSON(include = false)
    public ParameterType getParameterType() {
        return parameterType;
    }

    public void setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType;
    }

    @JSON
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public enum ParameterType {
        Unknown, Header, Path, Query
    }
}
