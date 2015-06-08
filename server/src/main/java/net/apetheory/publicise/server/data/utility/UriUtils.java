package net.apetheory.publicise.server.data.utility;

import net.apetheory.publicise.server.data.UriType;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;

/**
 * Utility class which contains some helper methods
 * for building and validating URIs
 *
 * @author Christoph Grundmann
 */
public class UriUtils {
    public static final int UNDEFINED_INTEGER_PARAMETER_VALUE = -1;

    /**
     * Builds the URI path for a specific URI type
     *
     * @param type    The type of the URI to build
     * @param uriInfo The URI info object containing all URI info
     * @return The relative URI
     */
    public static String buildUriPath(UriType type, UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
        int offset = getCurrentOffset(uriInfo);
        int limit = getCurrentLimit(uriInfo);

        UriBuilder builder =
                uriInfo.getAbsolutePathBuilder()
                        .scheme(null)
                        .host(null)
                        .port(-1);

        //reset all query parameters
        for (Map.Entry<String, List<String>> entry : queryParameters.entrySet()) {
            List<String> value = entry.getValue();
            String name = entry.getKey();

            if (isQueryParameterValid(name, value, "offset", "limit")) {
                builder.replaceQueryParam(entry.getKey(), value.get(0));
            }
        }

        //set limit parameter
        if (limit != UNDEFINED_INTEGER_PARAMETER_VALUE) {
            builder.replaceQueryParam("limit", String.valueOf(limit));
        }

        //set offset parameter
        if (offset != UNDEFINED_INTEGER_PARAMETER_VALUE) {
            if (!UriType.Self.equals(type)) {
                offset = UriType.Previous.equals(type) ? offset - 1 : offset + 1;
            }
            builder.replaceQueryParam("offset", String.valueOf(offset));
        }

        return builder.build().toString();
    }

    /**
     * Gets the current offset parameter value
     *
     * @param uriInfo The URI info object containing all URI info
     * @return The current offset or UriUtils.UNDEFINED_OFFSET_PARAMETER_VALUE if field does not exist
     */
    public static int getCurrentOffset(UriInfo uriInfo) {
        return getQueryParameterIntValue(uriInfo, "offset");
    }

    /**
     * Gets the current limit parameter value
     *
     * @param uriInfo The URI info object containing all URI info
     * @return The current limit or UriUtils.UNDEFINED_LIMIT_PARAMETER_VALUE if field does not exist
     */
    public static int getCurrentLimit(UriInfo uriInfo) {
        return getQueryParameterIntValue(uriInfo, "limit");
    }

    private static int getQueryParameterIntValue(UriInfo uriInfo, String parameterName) {
        MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();

        int limit = UNDEFINED_INTEGER_PARAMETER_VALUE;
        if (queryParameters != null && queryParameters.containsKey(parameterName)) {
            List<String> values = queryParameters.get(parameterName);
            String value = values.size() > 0 ? values.get(0) : null;

            if (!StringUtils.isNullOrEmpty(value)) {
                try {
                    limit = Integer.parseInt(value);
                } catch (NumberFormatException ignored) {
                }
            }
        }

        return limit;
    }

    /**
     * Checks whether the given query parameter is valid
     *
     * @param value    The value of the query parameter to test
     * @param name     The name of the query parameter to test
     * @param excludes List of excluded names
     * @return true if it is valid, false otherwise
     */
    public static boolean isQueryParameterValid(String name, List<String> value, String... excludes) {
        boolean isExcluded = StringUtils.isNullOrEmpty(name);

        if (!isExcluded) {
            for (String exclude : excludes) {
                if (name.equals(exclude)) {
                    isExcluded = true;
                    break;
                }
            }
        }

        return !isExcluded && value != null && value.size() > 0 &&
                !StringUtils.isNullOrEmpty(value.get(0));
    }
}
