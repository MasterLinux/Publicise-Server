package net.apetheory.publicise.server.api.parameter;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

/**
 * Created by Christoph on 19.09.2014.
 */
public class PaginationParameter {
    public static final String DEFAULT_LIMIT = "20";
    private final int limit;
    private final int offset;

    public PaginationParameter(
            @QueryParam("limit") @DefaultValue(DEFAULT_LIMIT) int limit,
            @QueryParam("offset") int offset
    ) {
        this.limit = limit;
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }
}
