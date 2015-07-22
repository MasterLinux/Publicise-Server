package net.apetheory.publicise.server.api;

import flexjson.JSONDeserializer;
import net.apetheory.publicise.server.Config;
import net.apetheory.publicise.server.api.documentation.meta.Description;
import net.apetheory.publicise.server.api.documentation.meta.Errors;
import net.apetheory.publicise.server.api.documentation.meta.Required;
import net.apetheory.publicise.server.api.error.ApiErrorException;
import net.apetheory.publicise.server.api.error.DatabaseConnectionError;
import net.apetheory.publicise.server.api.error.DatabaseInsertionError;
import net.apetheory.publicise.server.api.error.InternalServerError;
import net.apetheory.publicise.server.api.header.PrettyPrintHeader;
import net.apetheory.publicise.server.api.parameter.FieldsParameter;
import net.apetheory.publicise.server.api.parameter.PaginationParameter;
import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.database.Database;
import net.apetheory.publicise.server.data.database.dao.UsersDAO;
import net.apetheory.publicise.server.data.database.exception.ConnectionException;
import net.apetheory.publicise.server.data.database.exception.InsertionException;
import net.apetheory.publicise.server.resource.UserResource;
import org.glassfish.jersey.server.ManagedAsync;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/users")
@Description("API endpoint used to create and update and delete users")
public class UsersEndPoint extends BaseEndPoint {

    /**
     * Creates a new user
     * <p>
     * <br><br><b>Required header:</b><br>
     * Content-Type: application/json
     * <p>
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
    @ManagedAsync
    public void createUser(
            @Suspended AsyncResponse response,
            @BeanParam PrettyPrintHeader prettyPrint,
            String body
    ) {
        boolean isPrettyPrinted = prettyPrint.isPrettyPrinted();
        UserResource resource = new JSONDeserializer<UserResource>()
                .deserialize(body, UserResource.class);

        try {
            UsersDAO users = new UsersDAO(Database.fromConfig());
            users.insert(resource, (resourceSet, throwable) -> {
                if (throwable != null) {

                } else {
                    response.resume(Response.ok()
                            .type(MediaType.APPLICATION_JSON)
                            .entity(resourceSet.toJson(isPrettyPrinted))
                            .build());
                }
            });
        } catch (InsertionException e) {
            throw new ApiErrorException(new DatabaseInsertionError(), isPrettyPrinted);
        } catch (ConnectionException e) {
            throw new ApiErrorException(new DatabaseConnectionError(), isPrettyPrinted);
        } catch (Config.MissingNameException | Config.MissingConfigException e) {
            throw new ApiErrorException(new InternalServerError(), isPrettyPrinted);
        }
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
    @Errors({DatabaseConnectionError.class})
    @Description("Gets a specific user by its ID")
    public String getUserById(
            @BeanParam PrettyPrintHeader prettyPrint,
            @Required @PathParam("id") @Description("The ID of the user to get") String id
    ) {
        boolean isPrettyPrinted = prettyPrint.isPrettyPrinted();
        ResourceSet result;

        try {
            UsersDAO users = new UsersDAO(Database.fromConfig());
            result = users.getById(id);
        } catch (ConnectionException e) {
            throw new ApiErrorException(new DatabaseConnectionError(), isPrettyPrinted);
        } catch (Config.MissingNameException | Config.MissingConfigException e) {
            throw new ApiErrorException(new InternalServerError(), isPrettyPrinted);
        }

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
        ResourceSet result;

        try {
            UsersDAO users = new UsersDAO(Database.fromConfig());
            result = users.get(uriInfo, pagination.getOffset(), pagination.getLimit());
        } catch (ConnectionException e) {
            throw new ApiErrorException(new DatabaseConnectionError(), isPrettyPrinted);
        } catch (Config.MissingNameException | Config.MissingConfigException e) {
            throw new ApiErrorException(new InternalServerError(), isPrettyPrinted);
        }

        return result != null ? result.toJson(fields.getFields(), isPrettyPrinted) : null;
    }
}
