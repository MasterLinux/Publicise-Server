package net.apetheory.publicise.server.api.parameter;

import net.apetheory.publicise.server.data.utility.StringUtils;

import javax.ws.rs.QueryParam;

/**
 * Created by Christoph on 19.09.2014.
 */
public class FieldsParameter {
    private final String[] fields;

    public FieldsParameter(
            @QueryParam("fields") String fields
    ) {
        this.fields = StringUtils.isEmpty(fields) ? new String[]{} : fields.split(",");
    }

    public String[] getFields() {
        return fields;
    }
}
