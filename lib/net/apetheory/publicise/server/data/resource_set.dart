part of net.apetheory.publicise.server;

class ResourceSet<TResource extends Resource> {
  Meta _meta;

  /**
   * Result set which contains
   * all requested resources
   */
  final List<TResource> objects;

  /**
   * Gets the meta object of this
   * resource set
   */
  final Meta meta;

  /**
   * Initializes the resource set
   */
  ResourceSet(this.objects, this.meta);

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
