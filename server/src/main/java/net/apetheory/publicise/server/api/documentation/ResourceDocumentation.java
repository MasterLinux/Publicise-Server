package net.apetheory.publicise.server.api.documentation;

import flexjson.JSON;
import flexjson.JSONSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Christoph on 27.04.2015.
 */
public class ResourceDocumentation {
    private List<MethodDocumentation> httpMethods = new ArrayList<>();
    private Set<String> allowedMethods;
    private String path;

    @JSON
    public List<MethodDocumentation> getHttpMethods() {
        return httpMethods;
    }

    @JSON
    public String getPath() {
        return path;
    }

    @JSON
    public Set<String> getAllowedMethods() {
        return allowedMethods;
    }

    public String toJson(boolean isPrettyPrinted) {
        return new JSONSerializer()
                .prettyPrint(isPrettyPrinted)
                .exclude("*.class")
                .serialize(this);
    }

    static public class Builder {
        ResourceDocumentation documentation = new ResourceDocumentation();

        public Builder addMethod(MethodDocumentation method) {
            documentation.httpMethods.add(method);
            return this;
        }

        public Builder setPath(String path) {
            documentation.path = path;
            return this;
        }

        public Builder setAllowedMethods(Set<String> allowedMethods) {
            documentation.allowedMethods = allowedMethods;
            return this;
        }

        public ResourceDocumentation build() {
            return documentation;
        }
    }

}
