package net.apetheory.publicise.server.resource;

import net.apetheory.publicise.server.data.database.meta.DatabaseField;

/**
 * Created by Christoph on 13.09.2014.
 */
public class DocumentResource extends BaseResource {

    @DatabaseField
    private String title;

    @DatabaseField
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
