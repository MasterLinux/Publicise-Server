part of net.apetheory.publicise.server;

class Document extends Resource {
  String title;
  String subtitle;
  List<String> pages;

  toJSON() {
    return {
      "title": title,
      "subtitle": subtitle
    };
  }
}
