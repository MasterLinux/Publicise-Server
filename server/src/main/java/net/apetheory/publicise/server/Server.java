package net.apetheory.publicise.server;

import net.apetheory.publicise.server.data.database.Database;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private final HttpServer server;
    private URI uri;

    /**
     * Initializes the server
     *
     * @param uri The URI of the server
     */
    private Server(URI uri, List<String> packages) {
        this.uri = uri;

        // create server
        server = GrizzlyHttpServerFactory.createHttpServer(this.uri, createResourceConfig(packages));

        // make static content available via <base_url>/web/
        server.getServerConfiguration().addHttpHandler(
                new CLStaticHttpHandler(HttpServer.class.getClassLoader(), "/static/"),
                "/web"
        );
    }

    @NotNull
    private ResourceConfig createResourceConfig(List<String> packages) {
        ResourceConfig config = new ResourceConfig().packages("net.apetheory.publicise.server.api");

        for (String pack : packages) {
            config.packages(pack);
        }

        return config;
    }

    /**
     * Gets the base URI of the server
     *
     * @return The base URI
     */
    public URI getUri() {
        return uri;
    }

    /**
     * Starts the server
     */
    public void start() {
        //register shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Stopping server");
            stop();
        }, "shutdownHook"));

        //try to start the server
        try {
            logger.info("Starting server");
            server.start();
            Thread.currentThread().join();

        } catch (IOException | InterruptedException e) {
            logger.warning("Stopping server");
            stop();
        }
    }

    /**
     * Stops the server
     */
    public void stop() {
        if (!server.isStarted()) return;
        Database.fromConfig().disconnect();
        server.shutdownNow();
    }

    public static class Builder {
        private List<String> packages = new LinkedList<>();
        private URI uri;

        public Builder(URI uri) {
            this.uri = uri;
        }

        Builder packages(final String... packages) {
            Collections.addAll(this.packages, packages);
            return this;
        }

        Server build() {
            return new Server(uri, packages);
        }
    }
}