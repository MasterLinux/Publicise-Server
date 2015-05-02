package net.apetheory.publicise.server.api;

import net.apetheory.publicise.server.data.utility.StringUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.net.URL;

@Path("/docs")
public class DocumentationEndPoint {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getDocumentation() {  //TODO make async
        URL docsUrl = getClass().getClassLoader().getResource("documentation.html");
        String file = docsUrl != null ? docsUrl.getFile() : null;
        String html = null;

        if(!StringUtils.isNullOrEmpty(file)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder builder = new StringBuilder();
                reader.lines().forEach(builder::append);
                html = builder.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return html;
    }
}
