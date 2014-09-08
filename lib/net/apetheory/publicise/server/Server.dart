library net.apetheory.publicise.server;

import 'package:redstone/server.dart' as api;

main() {
  var server = new Server("localhost", 1337);
  server.start();
}

class Server {
  final String address;
  final int port;

  Server(this.address, this.port);

  void start() {
    api.setupConsoleLog();
    api.start(
        address: this.address,
        port: this.port
    );
  }
}