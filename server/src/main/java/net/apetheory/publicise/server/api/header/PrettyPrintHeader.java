package net.apetheory.publicise.server.api.header;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.HeaderParam;

/**
 * Header used to define whether the response is
 * pretty printed or not
 */
public class PrettyPrintHeader {
    private final boolean prettyPrint;

    public PrettyPrintHeader(@HeaderParam("X-Pretty-Print") @DefaultValue("true") boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    public boolean isPrettyPrinted() {
        return prettyPrint;
    }
}
