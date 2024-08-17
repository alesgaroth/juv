package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Node;
import java.util.HashMap;
import java.util.Map;

public class NodeInstance {
  Node design;
  ConnectionInstance [] connections;
  ConnectionInstance [] upstreams;

  public NodeInstance(Node design) {
    this.design = design;
    connections = new ConnectionInstance[design.getNumOutputs()];
    upstreams = new ConnectionInstance[design.getNumInputs()];
  }

  public Node getNode(){
    return design;
  }


  public static Iterable<NodeInstance> cloneOutputs(Iterable<Node> set){
    Map<Node, NodeInstance> nis = new HashMap<>();
    for(Node n: set) {
      NodeInstance ni = nis.computeIfAbsent(n, (k) -> new NodeInstance(k));
      for(int i = 0; i < ni.connections.length; i += 1) {
        ni.connections[i] = new ConnectionInstance(n.getOutput(i), nis);
      }
    }
    return nis.values();
  }

  public ConnectionInstance getOutput(int output){
    return connections[output];
  }

  public ConnectionInstance getInput(int input) {
    return upstreams[input];
  }

  public void setInput(ConnectionInstance ci, int input) {
    upstreams[input] = ci;
  }
}