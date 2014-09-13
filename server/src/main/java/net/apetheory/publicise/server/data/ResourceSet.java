package net.apetheory.publicise.server.data;

import com.google.gson.Gson;
import net.apetheory.publicise.server.resource.BaseResource;
import net.apetheory.publicise.server.resource.MetaModel;

import java.util.ArrayList;
import java.util.List;

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
     * Serializes this resource set to JSON
     * @return This resource set as JSON
     */
    public String toJson() {
        return new Gson().toJson(this);
    }

    /**
     * Builder used to build a ResourceSet
     */
    static public class Builder<TResource extends BaseResource> {
        private List<TResource> objects;
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
        public Builder addResource(TResource resource) {
            objects.add(resource);
            return this;
        }

        /**
         * Adds a collection of resources to the ResultSet
         *
         * @param resources The collection of resources to add
         * @return The Builder instance
         */
        public Builder addResources(List<TResource> resources) {
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
        public Builder setLimit(int limit) {
            meta.setLimit(limit);
            return this;
        }

        /**
         * Sets the page number
         *
         * @param offset
         * @return The Builder instance
         */
        public Builder setOffset(int offset) {
            meta.setOffset(offset);
            return this;
        }

        /**
         * Sets the URI which points to the next
         * result set of resources
         *
         * @param next
         * @return The Builder instance
         */
        public Builder setNext(String next) { //TODO can be done with the offset value
            meta.setNext(next);
            return this;
        }

        /**
         * Sets the URI which points to the previous
         * result set of resources
         *
         * @param prev
         * @return The Builder instance
         */
        public Builder setPrev(String prev) {
            meta.setPrev(prev);
            return this;
        }

        public ResourceSet<TResource> build() {
            meta.setFilteredCount(objects.size());

            return new ResourceSet<>(objects, meta);
        }
    }
}
