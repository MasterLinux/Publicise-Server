part of net.apetheory.publicise.server;

/**
 * #Get a specific document
 *    GET <base_url>/documents/<id>
 *
 * #Get all documents
 *    GET <base_url>/documents
 *
 * #Delete a specific document
 *    DELETE <base_url>/documents/<id>
 *
 * #Update a specific document
 *    PUT <base_url>/documents/<id>
 *
 *
 *
 * #Create a new document
 *    POST <base_url>/documents
 *
 * ###Header
 * content-type => application/json
 */
@api.Group("/documents")
class Documents {

  @api.Route("/?", methods: const [api.GET])
  String getDocuments(String id) {
    var doc = new Document();
    doc.id = 1;
    doc.pages = new List<String>();
    doc.pages.add("content_1");
    doc.pages.add("content_2");
    doc.subtitle = "subtitle";

    var docs = new List<Document>();
    docs.add(doc);

    var set = new ResourceSet<Document>(docs, docs.length);

    return set.toJSON();
  }

  @api.Route("/:id", methods: const [api.GET])
  String getDocumentById(String id) => "get doc with id: $id";

  @api.Route("/?", methods: const [api.POST])
  String createDocument(@api.Body(api.JSON) Map user) => "add document";

  @api.Route("/:id", methods: const [api.PUT])
  String updateDocument(@api.Body(api.JSON) Map user, String id) => "update document with id: $id";

  @api.Route("/:id", methods: const [api.DELETE])
  String deleteDocument(String id) => "delete document with id: $id";
}
