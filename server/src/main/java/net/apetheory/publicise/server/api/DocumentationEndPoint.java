package net.apetheory.publicise.server.api;

import net.apetheory.publicise.server.api.documentation.converter.JsonConverter;
import net.apetheory.publicise.server.api.documentation.meta.Description;
import net.apetheory.publicise.server.api.error.ResourceUnavailableError;
import net.apetheory.publicise.server.api.documentation.meta.Errors;
import net.apetheory.publicise.server.api.documentation.model.DocumentationModel;
import net.apetheory.publicise.server.api.documentation.reader.DocumentationReader;
import net.apetheory.publicise.server.api.header.PrettyPrintHeader;
import net.apetheory.publicise.server.data.ApiErrorException;
import net.apetheory.publicise.server.data.ResourceFileReader;
import net.apetheory.publicise.server.data.utility.StringUtils;
import org.glassfish.jersey.server.ManagedAsync;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/docs")
@Description("Resource which is used to provide the api documentation as web-page")
public class DocumentationEndPoint {

    @OPTIONS
    @ManagedAsync
    @Produces(MediaType.APPLICATION_JSON)
    @Description("Gets the documentation in its JSON representation")
    public void getOptions(
            @Suspended AsyncResponse response,
            @BeanParam PrettyPrintHeader prettyPrint
    ) {
        DocumentationModel documentation = new DocumentationReader(
                DocumentationEndPoint.class,
                DocumentsEndPoint.class
        ).read();

        response.resume(Response.ok()
                .type(MediaType.APPLICATION_JSON)
                .entity(JsonConverter.toJSON(documentation, prettyPrint.isPrettyPrinted()))
                .build());
    }

    @GET
    @ManagedAsync
    @Errors(ResourceUnavailableError.class)
    @Produces(MediaType.TEXT_HTML)
    @Description("Gets the documentation in its HTML representation")
    public void getDocumentation(
            @Suspended AsyncResponse response,
            @BeanParam PrettyPrintHeader prettyPrint
    ) {
        String html = new ResourceFileReader("documentation.html").read();

        if (StringUtils.isNullOrEmpty(html)) {
            response.resume(new ApiErrorException(new ResourceUnavailableError(), prettyPrint.isPrettyPrinted()));
        } else {
            response.resume(Response.ok()
                    .type(MediaType.TEXT_HTML)
                    .entity(html)
                    .build());
        }
    }
}
