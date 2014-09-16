package net.apetheory.publicise.server.data;

import net.apetheory.publicise.server.data.converter.JsonConverter;
import net.apetheory.publicise.server.resource.BaseResource;
import net.apetheory.publicise.server.resource.MetaModel;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Christoph on 13.09.2014.
 */
public class ResourceSet<TResource extends BaseResource> {
    private List<TResource> objects;
    private MetaModel meta;

    public static final int DEFAULT_OFFSET = 0;
    public static final int DEFAULT_LIMIT = 10;

    private ResourceSet(List<TResource> objects, MetaModel meta) {
        this.objects = objects;
        this.meta = meta;
    }

    public List<TResource> getObjects() {
        return objects;
    }

    public MetaModel getMeta() {
        return meta;
    }

    /**
     * Serializes this resource set to a JSON formatted string
     * @return This resource set as JSON formatted string
     */
    public String toJson() {
        return toJson(null);
    }

    public String toJson(String fields) {
        return JsonConverter.toJSON(this, fields);
    }

    /**
     * Builder used to build a ResourceSet
     */
    static public class Builder<TResource extends BaseResource> {
        private static final int EMPTY_OFFSET_PARAMETER_VALUE = -1;
        private static final int EMPTY_LIMIT_PARAMETER_VALUE = -1;
        private List<TResource> objects;
        private UriInfo uriInfo;
        private MetaModel meta;

        /**
         * Initializes the ResourceSet builder
         * @param totalCount Number of available resources
         */
        public Builder(long totalCount) {
            objects = new ArrayList<>();

            meta = new MetaModel();
            meta.setTotalCount(totalCount);
            meta.setLimit(DEFAULT_LIMIT);
            meta.setOffset(DEFAULT_OFFSET);
        }

        /**
         * Adds a new resource to the ResultSet
         *
         * @param resource The resource to add
         * @return The Builder instance
         */
        public Builder<TResource> addResource(TResource resource) {
            objects.add(resource);
            return this;
        }

        /**
         * Adds a collection of resources to the ResultSet
         *
         * @param resources The collection of resources to add
         * @return The Builder instance
         */
        public Builder<TResource> addResources(List<TResource> resources) {
            objects.addAll(resources);
            return this;
        }

        /**
         * Sets the maximum number of resources
         * in the ResourceSet
         *
         * @param limit
         * @return The Builder instance
         */
        public Builder<TResource> setLimit(int limit) {
            meta.setLimit(limit);
            return this;
        }

        /**
         * Sets the page number
         *
         * @param offset
         * @return The Builder instance
         */
        public Builder<TResource> setOffset(int offset) {
            meta.setOffset(offset);
            return this;
        }

        /**
         * Sets the URI info used to create the
         * value of the prev and next field in meta
         *
         * @param uriInfo The URI info of the resource endpoint
         * @return The Builder instance
         */
        public Builder<TResource> setUriInfo(UriInfo uriInfo) {
            this.uriInfo = uriInfo;
            return this;
        }

        /**
         * Builds the ResourceSet
         * @return The ResourceSet
         */
        public ResourceSet<TResource> build() {
            meta.setFilteredCount(objects.size());

            if(uriInfo != null) {
                setPrev(meta, uriInfo);
                setNext(meta, uriInfo);
            }

            return new ResourceSet<>(objects, meta);
        }

        /**
         * Sets the URI which points to the previous
         * result set of resources
         */
        private void setPrev(MetaModel meta, UriInfo uriInfo) {
            if (meta.getOffset() > 0) {
                meta.setPrev(buildUriPath(UriType.Previous, uriInfo));
            }
        }

        /**
         * Sets the URI which points to the next
         * result set of resources
         */
        private void setNext(MetaModel meta, UriInfo uriInfo) {
            long rest = meta.getTotalCount() - meta.getLimit() * (meta.getOffset() + 1);
            if(rest > 0) {
                meta.setNext(buildUriPath(UriType.Next, uriInfo));
            }
        }

        private int getCurrentOffset(UriInfo uriInfo) {
            MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();

            int offset = EMPTY_OFFSET_PARAMETER_VALUE;
            if(queryParameters.containsKey("offset")) {
                offset = Integer.parseInt(queryParameters.get("offset").get(0));
            }

            return offset;
        }

        private int getCurrentLimit(UriInfo uriInfo) {
            MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();

            int limit = EMPTY_LIMIT_PARAMETER_VALUE;
            if(queryParameters.containsKey("limit")) {
                limit = Integer.parseInt(queryParameters.get("limit").get(0));
            }

            return limit;
        }

        private String buildUriPath(UriType type, UriInfo uriInfo) {
            MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
            int offset = getCurrentOffset(uriInfo);
            int limit = getCurrentLimit(uriInfo);

            UriBuilder builder =
                    uriInfo.getAbsolutePathBuilder()
                        .scheme(null)
                        .host(null);

            //reset all query parameters
            for(Map.Entry<String, List<String>> entry : queryParameters.entrySet()) {
                List<String> value = entry.getValue();
                String name = entry.getKey();

                if(isQueryParameterValid(value, name)) {
                    builder.replaceQueryParam(entry.getKey(), value.get(0));
                }
            }

            //set limit parameter
            if(limit != EMPTY_LIMIT_PARAMETER_VALUE) {
                builder.replaceQueryParam("limit", String.valueOf(limit));
            }

            //set offset parameter
            if(offset != EMPTY_OFFSET_PARAMETER_VALUE) {
                offset = type.equals(UriType.Previous) ? offset - 1 : offset + 1;
                builder.replaceQueryParam("offset", String.valueOf(offset));
            }

            return builder.build().toString();
        }

        /**
         * Checks whether the query parameter is valid
         * @param value The value of the query parameter
         * @param name The name of the query parameter
         * @return true if it is valid, false otherwise
         */
        private boolean isQueryParameterValid(List<String> value, String name) {
            return !name.equals("limit") &&
                    !name.equals("offset") &&
                    value.size() > 0 &&
                    !StringUtils.isEmpty(value.get(0));
        }

        private enum UriType {
            Previous, Next
        }
    }
}
