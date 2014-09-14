package net.apetheory.publicise.server.resource;

import net.apetheory.publicise.server.data.database.meta.DatabaseId;

/**
 * Created by Christoph on 13.09.2014.
 */
public class BaseResource {

    @DatabaseId
    private String id;

    private String resourceUri;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }
}
