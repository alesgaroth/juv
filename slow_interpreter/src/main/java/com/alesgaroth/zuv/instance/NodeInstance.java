package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Connection;
import com.alesgaroth.zuv.design.Node;

import java.util.Arrays;
import java.util.Collections;

// A NodeInstance is analogous to a stack frame in a normal running system
public class NodeInstance<N extends Node> implements Runnable {
  N design;
  ConnectionInstance [] connections;
  ConnectionInstance [] upstreams;

  public NodeInstance(N design) {
    this.design = design;
    connections = new ConnectionInstance[design.getNumOutputs()];
    upstreams = new ConnectionInstance[design.getNumInputs()];
  }

  public N getNode() {
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

  public Iterable<ConnectionInstance> getInputs() {
    return Collections.unmodifiableList(Arrays.asList(upstreams));
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

  public boolean inputsReady() {
    for(ConnectionInstance ci: getInputs()) {
      if (!ci.isReady()){
        return false;
      }
    }
    return true;
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
