package net.apetheory.publicise.server;

import java.util.logging.Logger;
import java.io.IOException;
import java.net.URI;

public class Main {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    /**
     * Runs the server
     * @param args
     */
    public static void main(String[] args)
    {
        final Server server = new Server(URI.create("http://127.0.0.1:9080"));

        //register shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Stopping server");

            server.stop();
        }, "shutdownHook"));

        //try to start the server
        try {
            logger.info("Starting server");
            server.start();
            Thread.currentThread().join();

        } catch (IOException | InterruptedException e) {
            logger.warning("Stopping server");
            server.stop();
        }
    }
}
