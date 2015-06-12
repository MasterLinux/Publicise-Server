package net.apetheory.publicise.server.data.database.dao;

import net.apetheory.publicise.server.data.database.Database;
import net.apetheory.publicise.server.data.database.ResourceProvider;
import net.apetheory.publicise.server.resource.UserResource;

/**
 * Data access object which is used to insert,
 * update, delete and get users
 */
public class UsersDAO extends ResourceProvider<UserResource> {
    public static final String collectionName = "Users";

    /**
     * Initializes the user DAO
     *
     * @param database The database to insert the new user
     */
    public UsersDAO(Database database) {
        super(database, collectionName, UserResource.class);
    }
}
