package net.apetheory.publicise.server.api;

import net.apetheory.publicise.server.Config;
import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.database.Database;
import net.apetheory.publicise.server.data.database.dao.DocumentsDAO;
import net.apetheory.publicise.server.resource.DocumentResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/documents")
public class DocumentsEndPoint {

    /**
     * Gets a document by its ID
     *
     * @param id The ID of the document to get
     * @return The response as JSON string
     */
    @GET
    @Path("/{id: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserById(@PathParam("id") int id) {
        Database db = Database.fromConfig(Config.load("C:/config.json"));
        DocumentResource doc = new DocumentResource();

        doc.setTitle("testTitle");
        doc.setSubtitle("testSubtitle");

        DocumentsDAO.insertInto(db, doc, (resourceSet) -> {
            ResourceSet s = resourceSet;
        }, () -> {
            //TODO handle error
        });
        return id + "";
    }
}
