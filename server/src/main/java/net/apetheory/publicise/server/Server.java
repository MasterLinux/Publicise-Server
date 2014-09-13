package net.apetheory.publicise.server;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Server {
    private final HttpServer server;
    private URI uri;

    /**
     * Initializes the server
     * @param uri The URI of the server
     */
    public Server(URI uri) {
        this.uri = uri;

        //create server
        server = GrizzlyHttpServerFactory.createHttpServer(
                this.uri,
                new ResourceConfig()
                        .packages("net.apetheory.publicise.server.api")
        );
    }

    /**
     * Gets the base URI of the server
     * @return The base URI
     */
    public URI getUri() {
        return uri;
    }

    /**
     * Starts the server
     * @throws IOException
     */
    public void start() throws IOException {
        server.start();


    }

    /**
     * Stops the server
     */
    public void stop() {
        if(!server.isStarted()) return;
        server.shutdownNow();
    }
}