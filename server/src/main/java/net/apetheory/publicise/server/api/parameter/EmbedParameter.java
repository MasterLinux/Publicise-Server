package net.apetheory.publicise.server.api.parameter;

import net.apetheory.publicise.server.data.utility.StringUtils;

import javax.ws.rs.QueryParam;

/**
 * Parameter which is used to embed the response
 * with another resources
 */
public class EmbedParameter {
    private final String[] fields;

    public EmbedParameter(@QueryParam("embed") String fields) {
        this.fields = StringUtils.isNullOrEmpty(fields) ? new String[]{} : fields.split(",");
    }

    public String[] getFields() {
        return fields;
    }
}
