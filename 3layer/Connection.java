package com.alesagorth.zuv.threelayer.operator;

public class Connection {
  Set<NodePort> listeningSet = new HashSet<>();
  record NodePort(Node node, int port) {}
}
