package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Connection;
import com.alesgaroth.zuv.design.Node;

import java.util.Arrays;
import java.util.Collections;

// A NodeInstance is analogous to a stack frame in a normal running system
public class NodeInstance implements Runnable {
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

  public ConnectionInstance getOutput(int output) {
    if (!Node.validPut(output, connections.length)) {
      throw new Node.BadConnectionException();
    }
    return connections[output];
  }

  public Iterable<ConnectionInstance> getOutputs() {
    return Collections.unmodifiableList(Arrays.asList(connections));
  }

  public void setOutput(ConnectionInstance ci, int output) {
    if (!Node.validPut(output, connections.length)) {
      throw new Node.BadConnectionException();
    }
    connections[output] = ci;
  }

  public ConnectionInstance getInput(int input) {
    if (!Node.validPut(input, upstreams.length)) {
      throw new Node.BadConnectionException();
    }
    return upstreams[input];
  }

  public void setInput(ConnectionInstance ci, int input) {
    if (!Node.validPut(input, upstreams.length)) {
      throw new Node.BadConnectionException();
    }
    upstreams[input] = ci;
  }

  public void run() {
  }
}
