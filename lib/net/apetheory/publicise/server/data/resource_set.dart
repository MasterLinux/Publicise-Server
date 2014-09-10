part of net.apetheory.publicise.server;

class ResourceSet<TResource extends Resource> {
  Meta _meta;

  /**
   * Result set which contains
   * all requested resources
   */
  final List<TResource> objects;

  /**
   * Initializes the resource set
   */
  ResourceSet(this.objects, int totalCount) {
    _meta = new Meta();
    _meta.filteredCount = objects.length;
    _meta.totalCount = totalCount;
    //TODO add all meta info to meta
  }

  /**
   * Gets the meta object of this
   * resource set
   */
  Meta get meta {
    return _meta;
  }

  /**
   * Gets the JSON representation
   * of this resource set
   */
  String toJSON() {
    var result = <String>[];

    for(var resource in objects) {
      result.add(resource.toJSON());
    }

    return JSON.encode({
        "meta": meta.toJSON(),
        "objects": result
    });
  }
}
