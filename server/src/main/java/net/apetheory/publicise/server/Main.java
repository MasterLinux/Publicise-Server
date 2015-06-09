package net.apetheory.publicise.server;

import java.net.URI;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    /**
     * Runs the server
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO read uri with port from config
        new Server.Builder(URI.create("http://127.0.0.1:9080")).build().start();
    }
}
