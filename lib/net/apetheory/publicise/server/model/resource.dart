part of net.apetheory.publicise.server;

/**
 * Base implementation of a resource
 */
abstract class Resource {
  /**
   * Unique ID of this resource
   */
  int id;

  /**
   * Unique resource URI of this
   * resource
   */
  String resourceUri;

  /**
   * Gets the JSON representation
   * of this resource
   */
  toJSON();
}
