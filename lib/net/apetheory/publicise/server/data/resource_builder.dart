part of net.apetheory.publicise.server;

/**
 * Builds a specific [ResourceSet] for
 * a specific resource of type [TResource]
 */
class ResourceBuilder<TResource> {
  List<TResource> _objects;
  Meta _meta;

  const DEFAULT_LIMIT = 20;
  const DEFAULT_OFFSET = 0;

  ResourceBuilder(List<TResource> objects, int totalCount) {
    _objects = objects;

    _meta = new Meta();
    _meta.filteredCount = objects.length;
    _meta.totalCount = totalCount;
    _meta.offset = DEFAULT_OFFSET;
    _meta.limit = DEFAULT_LIMIT;
  }

  ResourceBuilder<TResource> setLimit(int limit) {
    _meta.limit = limit;
    return this;
  }

  ResourceBuilder<TResource> setOffset(int offset) {
    _meta.offset = offset;
    return this;
  }

  ResourceBuilder<TResource> setNext(String next) {
    _meta.next = next;
    return this;
  }

  ResourceBuilder<TResource> setPrev(String prev) {
    _meta.prev = prev;
    return this;
  }

  TResource build() {
    return new ResourceSet<TResource>(_objects, _meta);
  }
}
