package net.apetheory.publicise.server.api;

import flexjson.JSONDeserializer;
import net.apetheory.publicise.server.api.documentation.meta.Description;
import net.apetheory.publicise.server.api.documentation.meta.Errors;
import net.apetheory.publicise.server.api.documentation.meta.Required;
import net.apetheory.publicise.server.api.header.PrettyPrintHeader;
import net.apetheory.publicise.server.api.parameter.FieldsParameter;
import net.apetheory.publicise.server.api.parameter.PaginationParameter;
import net.apetheory.publicise.server.data.ApiErrorException;
import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.database.Database;
import net.apetheory.publicise.server.data.database.dao.UsersDAO;
import net.apetheory.publicise.server.data.database.error.ConnectionError;
import net.apetheory.publicise.server.resource.UserResource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/users")
@Description("API endpoint used to create and update and delete users")
public class UsersEndPoint extends BaseEndPoint {

    /**
     * Creates a new user
     *
     * <br><br><b>Required header:</b><br>
     * Content-Type: application/json
     *
     * <br><br><b>Body example:</b><br>
     * The document to add must be a JSON like
     * the following example. Each field is required
     * and must be non null.
     * <pre>
     * {@code
     * {
     *     "name": "user_name",
     *     "password": "user_password",
     *     "type": 0
     * }
     * }
     * </pre>
     *
     * @return The created user without the password in its JSON representation
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Description("Creates a new user")
    public String createUser(@BeanParam PrettyPrintHeader prettyPrint, String body) {
        boolean isPrettyPrinted = prettyPrint.isPrettyPrinted();

        UserResource resource = new JSONDeserializer<UserResource>()
                    .deserialize(body, UserResource.class);

        ResourceSet result = UsersDAO.insertInto(Database.fromConfig(), resource, (error) -> {
            throw new ApiErrorException(error, isPrettyPrinted);
        });

        return result != null ? result.toJson(isPrettyPrinted) : null;
    }

    /**
     * Gets a user by its ID
     *
     * @param id The ID of the user to get
     * @return The user in its JSON representation
     */
    @GET
    @Path("/{id: [0-9a-zA-Z]+}")
    @Produces(MediaType.APPLICATION_JSON)
    @Errors({ConnectionError.class})
    @Description("Gets a specific user by its ID")
    public String getUserById(
            @BeanParam PrettyPrintHeader prettyPrint,
            @Required @PathParam("id") @Description("The ID of the user to get") String id
    ) {
        boolean isPrettyPrinted = prettyPrint.isPrettyPrinted();

        ResourceSet result = UsersDAO.getByIdFrom(Database.fromConfig(), id, (error) -> {
            throw new ApiErrorException(error, isPrettyPrinted);
        });

        return result != null ? result.toJson(isPrettyPrinted) : null;
    }

    /**
     * Gets all users
     *
     * @return All users in there JSON representation
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Description("Gets all registered users")
    public String getDocuments(
            @BeanParam PrettyPrintHeader prettyPrint,
            @BeanParam PaginationParameter pagination,
            @BeanParam FieldsParameter fields,
            @Context UriInfo uriInfo
    ) {
        boolean isPrettyPrinted = prettyPrint.isPrettyPrinted();

        ResourceSet result = UsersDAO.getFrom(Database.fromConfig(), uriInfo, pagination.getOffset(), pagination.getLimit(), (error) -> {
            throw new ApiErrorException(error, isPrettyPrinted);
        });

        return result != null ? result.toJson(fields.getFields(), isPrettyPrinted) : null;
    }
}
