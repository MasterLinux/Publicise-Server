package net.apetheory.publicise.server.resource;

/**
 * Created by Christoph on 13.09.2014.
 */
public abstract class BaseResource {
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
