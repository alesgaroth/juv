package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Connection;
import com.alesgaroth.zuv.design.Node;

// A NodeInstance is analogous to a stack frame in a normal running system
public class NodeInstance {
  Node design;
  ConnectionInstance [] connections;
  ConnectionInstance [] upstreams;

  public NodeInstance(Node design) {
    this.design = design;
    connections = new ConnectionInstance[design.getNumOutputs()];
    upstreams = new ConnectionInstance[design.getNumInputs()];
  }

  public Node getNode() {
    return design;
  }

  ConnectionInstance getOutput(int output) {
    return connections[output];
  }

  void setOutput(ConnectionInstance ci, int output) {
    connections[output] = ci;
  }

  ConnectionInstance getInput(int input) {
    return upstreams[input];
  }

  void setInput(ConnectionInstance ci, int input) {
    upstreams[input] = ci;
  }
}
