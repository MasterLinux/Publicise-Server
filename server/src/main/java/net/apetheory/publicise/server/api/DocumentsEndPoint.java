package net.apetheory.publicise.server.api;

import flexjson.JSONDeserializer;
import net.apetheory.publicise.server.Config;
import net.apetheory.publicise.server.api.parameter.FieldsParameter;
import net.apetheory.publicise.server.api.parameter.PaginationParameter;
import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.database.Database;
import net.apetheory.publicise.server.data.database.dao.DocumentsDAO;
import net.apetheory.publicise.server.resource.DocumentResource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/documents")
public class DocumentsEndPoint {

    /**
     * Creates a new new document
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
     *     "title": "document_title",
     *     "subtitle": "document_subtitle"
     * }
     * }
     * </pre>
     *
     * @return The added document in its JSON representation
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createDocument(String body) {
        Database db = Database.fromConfig(Config.load("C:/config.json"));
        DocumentResource resource = new JSONDeserializer<DocumentResource>()
                    .deserialize(body, DocumentResource.class);

        ResourceSet result = DocumentsDAO.insertInto(db, resource, () -> {
            //TODO handle error
        });

        return result != null ? result.toJson() : null;
    }

    /**
     * Gets a document by its ID
     *
     * @param id The ID of the document to get
     * @return The document in its JSON representation
     */
    @GET
    @Path("/{id: [0-9a-zA-Z]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDocumentById(@PathParam("id") String id) {
        Database db = Database.fromConfig(Config.load("C:/config.json"));

        ResourceSet result = DocumentsDAO.getByIdFrom(db, id, () -> {
            //TODO handle error
        });

        return result != null ? result.toJson() : null;
    }

    /**
     * Gets all documents
     *
     * @return All documents as JSON response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDocuments(
            @BeanParam PaginationParameter pagination,
            @BeanParam FieldsParameter fields,
            @Context UriInfo uriInfo
    ) {
        Database db = Database.fromConfig(Config.load("C:/config.json"));

        ResourceSet result = DocumentsDAO.getFrom(db, uriInfo, pagination.getOffset(), pagination.getLimit(), () -> {
            //TODO handle error
        });

        return result != null ? result.toJson(fields.getFields()) : null;
    }
}
