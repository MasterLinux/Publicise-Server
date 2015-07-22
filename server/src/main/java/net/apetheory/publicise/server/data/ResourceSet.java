package net.apetheory.publicise.server.data;

import net.apetheory.publicise.server.data.converter.JsonConverter;
import net.apetheory.publicise.server.data.utility.UriUtils;
import net.apetheory.publicise.server.resource.BaseResource;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christoph on 13.09.2014.
 */
public class ResourceSet<TResource extends BaseResource> {
    private List<TResource> objects;
    private Meta meta;

    public static final int DEFAULT_OFFSET = 0;
    public static final int DEFAULT_LIMIT = 10;

    private ResourceSet(List<TResource> objects, Meta meta) {
        this.objects = objects;
        this.meta = meta;
    }

    public List<TResource> getObjects() {
        return objects;
    }

    public Meta getMeta() {
        return meta;
    }

    /**
     * Converts the resource set to a JSON formatted string
     *
     * @return The resource set in its JSON representation
     */
    public String toJson() {
        return toJson(new String[]{}, true);
    }

    public String toJson(boolean prettyPrint) {
        return toJson(new String[]{}, prettyPrint);
    }

    public String toJson(@NotNull String[] fields) {
        return toJson(fields, true);
    }

    public String toJson(@NotNull String[] fields, boolean prettyPrint) {
        return JsonConverter.toJSON(this, fields, prettyPrint);
    }

    /**
     * Builder used to build a ResourceSet
     */
    static public class Builder<TResource extends BaseResource> {

        private List<TResource> objects;
        private UriInfo uriInfo;
        private Meta meta;

        /**
         * Initializes the ResourceSet builder
         *
         * @param totalCount Number of available resources
         */
        public Builder(long totalCount) {
            objects = new ArrayList<>();

            meta = new Meta();
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

        public Builder<TResource> setFilteredCount(long filteredCount) {
            meta.setFilteredCount(filteredCount);
            return this;
        }

        /**
         * Builds the ResourceSet
         *
         * @return The ResourceSet
         */
        public ResourceSet<TResource> build() {
            if (uriInfo != null) {
                setPrev(meta, uriInfo);
                setNext(meta, uriInfo);
            }

            return new ResourceSet<>(objects, meta);
        }

        /**
         * Sets the URI which points to the previous
         * result set of resources
         */
        private void setPrev(Meta meta, UriInfo uriInfo) {
            if (meta.getOffset() > 0) {
                meta.setPrev(UriUtils.buildUriPath(UriType.Previous, uriInfo));
            }
        }

        /**
         * Sets the URI which points to the next
         * result set of resources
         */
        private void setNext(Meta meta, UriInfo uriInfo) {
            long rest = meta.getTotalCount() - meta.getLimit() * (meta.getOffset() + 1);
            if (rest > 0) {
                meta.setNext(UriUtils.buildUriPath(UriType.Next, uriInfo));
            }
        }
    }

    /**
     * Model which contains each meta information
     * of the requested resource.
     */
    public static class Meta {
        private int limit;
        private int offset;
        private long filteredCount;
        private long totalCount;
        private String next;
        private String prev;

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public long getFilteredCount() {
            return filteredCount;
        }

        public void setFilteredCount(long filteredCount) {
            this.filteredCount = filteredCount;
        }

        public long getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(long totalCount) {
            this.totalCount = totalCount;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getPrev() {
            return prev;
        }

        public void setPrev(String prev) {
            this.prev = prev;
        }
    }
}
