package net.apetheory.publicise.server.resource;

import net.apetheory.publicise.server.data.database.meta.DatabaseField;
import net.apetheory.publicise.server.data.database.meta.Visibility;

/**
 * A resource model representing an user
 */
public class UserResource extends BaseResource {

    @DatabaseField
    private String name;

    @DatabaseField
    private Integer type;

    @DatabaseField(visibility = Visibility.HIDDEN)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password; //TODO encrypt password
    }
}
