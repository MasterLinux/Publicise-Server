package net.apetheory.publicise.server.api.documentation.model;

import flexjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christoph on 02.05.2015.
 */
public class DocumentationModel {
    List<ResourceModel> resources = new ArrayList<>();

    @JSON
    public List<ResourceModel> getResources() {
        return resources;
    }

    public void addResource(ResourceModel model) {
        resources.add(model);
    }
}
