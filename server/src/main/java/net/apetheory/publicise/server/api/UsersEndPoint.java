package net.apetheory.publicise.server.api;

import flexjson.JSONDeserializer;
import net.apetheory.publicise.server.Config;
import net.apetheory.publicise.server.api.documentation.meta.Description;
import net.apetheory.publicise.server.api.documentation.meta.Errors;
import net.apetheory.publicise.server.api.documentation.meta.Required;
import net.apetheory.publicise.server.api.error.ApiErrorException;
import net.apetheory.publicise.server.api.error.DatabaseConnectionError;
import net.apetheory.publicise.server.api.error.InternalServerError;
import net.apetheory.publicise.server.api.error.ResourceNotFoundError;
import net.apetheory.publicise.server.api.header.PrettyPrintHeader;
import net.apetheory.publicise.server.api.parameter.FieldsParameter;
import net.apetheory.publicise.server.api.parameter.PaginationParameter;
import net.apetheory.publicise.server.data.database.Database;
import net.apetheory.publicise.server.data.database.dao.UsersDAO;
import net.apetheory.publicise.server.data.utility.ApiErrorUtil;
import net.apetheory.publicise.server.data.utility.UriUtils;
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
        final boolean isPrettyPrinted = prettyPrint.isPrettyPrinted();
        final UserResource resource = new JSONDeserializer<UserResource>()
                .deserialize(body, UserResource.class);

        try {
            UsersDAO users = new UsersDAO(Database.fromConfig());

            users.insert(resource, (resourceSet, exception) -> {
                if (exception != null) {
                    response.resume(new ApiErrorException(ApiErrorUtil.getApiError(exception), isPrettyPrinted));
                    return;
                }

                response.resume(Response.created(UriUtils.getResourceUri(resourceSet, 0))
                        .type(MediaType.APPLICATION_JSON)
                        .entity(resourceSet.toJson(isPrettyPrinted))
                        .build());
            });

        } catch (Config.MissingNameException | Config.MissingConfigException e) {
            response.resume(new ApiErrorException(new InternalServerError(), isPrettyPrinted));
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
    @Errors({DatabaseConnectionError.class, ResourceNotFoundError.class})
    @Description("Gets a specific user by its ID")
    @ManagedAsync
    public void getUserById(
            @Suspended AsyncResponse response,
            @BeanParam PrettyPrintHeader prettyPrint,
            @Required @PathParam("id") @Description("The ID of the user to get") String id
    ) {
        final boolean isPrettyPrinted = prettyPrint.isPrettyPrinted();

        try {
            UsersDAO users = new UsersDAO(Database.fromConfig());

            users.getById(id, (resourceSet, exception) -> {
                if (exception != null) {
                    response.resume(new ApiErrorException(ApiErrorUtil.getApiError(exception), isPrettyPrinted));
                    return;
                }

                response.resume(Response.ok()
                        .type(MediaType.APPLICATION_JSON)
                        .entity(resourceSet.toJson(isPrettyPrinted))
                        .build());
            });

        } catch (Config.MissingNameException | Config.MissingConfigException e) {
            response.resume(new ApiErrorException(new InternalServerError(), isPrettyPrinted));
        }
    }

    /**
     * Gets all users
     *
     * @return All users in there JSON representation
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Description("Gets all registered users")
    @ManagedAsync
    public void getUsers(
            @Suspended AsyncResponse response,
            @BeanParam PrettyPrintHeader prettyPrint,
            @BeanParam PaginationParameter pagination,
            @BeanParam FieldsParameter fields,
            @Context UriInfo uriInfo
    ) {
        final boolean isPrettyPrinted = prettyPrint.isPrettyPrinted();

        try {
            UsersDAO users = new UsersDAO(Database.fromConfig());

            users.get(uriInfo, pagination.getOffset(), pagination.getLimit(), (resourceSet, exception) -> {
                if (exception != null) {
                    response.resume(new ApiErrorException(ApiErrorUtil.getApiError(exception), isPrettyPrinted));
                    return;
                }

                response.resume(Response.ok()
                        .type(MediaType.APPLICATION_JSON)
                        .entity(resourceSet.toJson(isPrettyPrinted))
                        .build());
            });

        } catch (Config.MissingNameException | Config.MissingConfigException e) {
            response.resume(new ApiErrorException(new InternalServerError(), isPrettyPrinted));
        }
    }

    @DELETE
    @Path("/{id: [0-9a-zA-Z]+}")
    @Errors({DatabaseConnectionError.class, ResourceNotFoundError.class})
    @Produces(MediaType.APPLICATION_JSON)
    @Description("Deletes a specific user by its ID")
    @ManagedAsync
    public void deleteUserById(
            @Suspended AsyncResponse response,
            @BeanParam PrettyPrintHeader prettyPrint,
            @Required @PathParam("id") @Description("The ID of the user to remove") String id
    ) {
        final boolean isPrettyPrinted = prettyPrint.isPrettyPrinted();

        try {
            UsersDAO users = new UsersDAO(Database.fromConfig());

            users.deleteById(id, (resourceSet, exception) -> {
                if (exception != null) {
                    response.resume(new ApiErrorException(ApiErrorUtil.getApiError(exception), isPrettyPrinted));
                    return;
                }

                response.resume(Response.ok()
                        .type(MediaType.APPLICATION_JSON)
                        .entity(resourceSet.toJson(isPrettyPrinted))
                        .build());
            });

        } catch (Config.MissingNameException | Config.MissingConfigException e) {
            response.resume(new ApiErrorException(new InternalServerError(), isPrettyPrinted));
        }
    }
}
