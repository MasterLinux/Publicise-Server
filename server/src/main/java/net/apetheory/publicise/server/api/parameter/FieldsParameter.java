package net.apetheory.publicise.server.api.parameter;

import net.apetheory.publicise.server.api.documentation.meta.Description;
import net.apetheory.publicise.server.data.utility.StringUtils;

import javax.ws.rs.QueryParam;

/**
 * Parameter which is used to define which fields
 * should be returned in the requested response
 */
public class FieldsParameter {
    private final String[] fields;

    public FieldsParameter(@QueryParam("fields") @Description("Limits which fields are returned") String fields) {
        this.fields = StringUtils.isNullOrEmpty(fields) ? new String[]{} : fields.split(",");
    }

    public String[] getFields() {
        return fields;
    }
}
