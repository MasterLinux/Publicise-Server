package net.apetheory.publicise.server.model;

/**
 * Created by Christoph on 13.09.2014.
 */
public class DatabaseConfigModel {
    private String host;
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
