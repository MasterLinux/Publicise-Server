part of net.apetheory.publicise.server;

/**
 * Meta model which contains all information
 * for pagination and filtering.
 */
class Meta {
  int offset;
  int limit;
  int filteredCount;
  int totalCount;
  String next;
  String prev;

  /**
   * Gets the JSON representation
   * of this meta model
   */
  toJSON() {
    return {
      "offset": offset,
      "limit": limit,
      "filteredCount": filteredCount,
      "totalCount": totalCount,
      "next": next,
      "prev": prev
    };
  }
}
