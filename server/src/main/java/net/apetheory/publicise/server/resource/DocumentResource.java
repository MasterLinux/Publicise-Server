package net.apetheory.publicise.server.resource;

/**
 * Created by Christoph on 13.09.2014.
 */
public class DocumentResource extends BaseResource {
    private String title;
    private String subtitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
