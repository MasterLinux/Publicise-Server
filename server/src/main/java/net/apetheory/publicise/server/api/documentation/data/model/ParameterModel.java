package net.apetheory.publicise.server.api.documentation.data.model;

import flexjson.JSON;

/**
 * Created by Christoph on 27.04.2015.
 */
public class ParameterModel {
    private String name;
    private String type;
    private String description;
    private boolean isRequired;

    @JSON
    public String getName() {
        return name;
    }

    @JSON
    public String getType() {
        return type;
    }

    @JSON
    public String getDescription() {
        return description;
    }

    @JSON
    public boolean isRequired() {
        return isRequired;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }
}
