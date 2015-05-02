package net.apetheory.publicise.server.api;

import net.apetheory.publicise.server.api.documentation.converter.JsonConverter;
import net.apetheory.publicise.server.api.documentation.model.ResourceModel;
import net.apetheory.publicise.server.api.documentation.reader.ResourceReader;
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
        ResourceModel resource = new ResourceReader(this.getClass()).read();
        String response = JsonConverter.toJSON(resource, prettyPrint.isPrettyPrinted());

        return Response.ok()
                .allow(resource.getAllowedMethods())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(response)
                .build();
    }
}
