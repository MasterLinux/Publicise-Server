package net.apetheory.publicise.server.api;

import net.apetheory.publicise.server.api.documentation.DocumentationBuilder;
import net.apetheory.publicise.server.api.documentation.ResourceDocumentation;
import net.apetheory.publicise.server.api.documentation.data.model.ResourceModel;
import net.apetheory.publicise.server.api.documentation.data.reader.ResourceReader;
import net.apetheory.publicise.server.api.header.PrettyPrintHeader;

import javax.ws.rs.BeanParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



/**
 * Created by Christoph on 27.04.2015.
 */
public abstract class BaseEndPoint {

    @OPTIONS
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptions(@BeanParam PrettyPrintHeader prettyPrint) {
        ResourceDocumentation documentation = new DocumentationBuilder(this.getClass()).build();

        ResourceModel resource = new ResourceReader(this.getClass()).read();

        return Response.ok()
                .allow(documentation.getAllowedMethods())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(documentation.toJson(prettyPrint.isPrettyPrinted()))
                .build();
    }
}
