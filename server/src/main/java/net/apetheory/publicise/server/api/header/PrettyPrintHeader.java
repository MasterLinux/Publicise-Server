package net.apetheory.publicise.server.api.header;

import net.apetheory.publicise.server.api.documentation.meta.Description;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.HeaderParam;

/**
 * Header used to define whether the response is
 * white-space compressed or not
 */
public class PrettyPrintHeader {
    private final boolean prettyPrint;

    public PrettyPrintHeader(@HeaderParam("X-Pretty-Print") @DefaultValue("true") @Description("Enables or disables white-space compressed output") boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    public boolean isPrettyPrinted() {
        return prettyPrint;
    }
}
