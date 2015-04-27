package net.apetheory.publicise.server.api.documentation;

import flexjson.JSON;

/**
 * Created by Christoph on 27.04.2015.
 */
public class ParameterDocumentation {
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

    static public class Builder {
        private ParameterDocumentation documentation = new ParameterDocumentation();

        public Builder setName(String name) {
            documentation.name = name;
            return this;
        }

        public Builder setType(String type) {
            documentation.type = type;
            return this;
        }

        public Builder setDescription(String description) {
            documentation.description = description;
            return this;
        }

        public Builder setIsRequired(boolean isRequired) {
            documentation.isRequired = isRequired;
            return this;
        }

        public ParameterDocumentation build() {
            return documentation;
        }
    }
}
