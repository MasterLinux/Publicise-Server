library net.apetheory.publicise.server;

import 'package:redstone/server.dart' as api;
import 'package:args/args.dart';
import 'dart:convert';

part 'model/resource.dart';
part 'model/document.dart';
part 'model/meta.dart';
part 'resource/documents.dart';
part 'data/resource_set.dart';


main(List<String> args) {
  var parser = new ArgParser();
  parser.addOption('address', abbr: 'a', defaultsTo: Server.defaultAddress);
  parser.addOption('port', abbr: 'p', defaultsTo: Server.defaultPort);

  var results = parser.parse(args);

  var server = new Server(results['address'], int.parse(results['port']));
  server.start();
}

class Server {
  static String defaultAddress = 'localhost';
  static int defaultPort = 1337;
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