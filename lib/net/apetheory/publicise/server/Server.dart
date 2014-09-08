library net.apetheory.publicise.server;

import 'package:redstone/server.dart' as api;
import 'package:args/args.dart';

main(List<String> args) {
  var parser = new ArgParser();
  parser.addOption('address', abbr: 'a', defaultsTo: 'localhost');
  parser.addOption('port', abbr: 'p', defaultsTo: 1337);

  var results = parser.parse(args);

  var server = new Server(results['address'], int.parse(results['port']));
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