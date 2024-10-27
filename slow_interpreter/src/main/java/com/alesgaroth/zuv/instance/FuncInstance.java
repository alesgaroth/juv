package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Connection;
import com.alesgaroth.zuv.design.Func;

import java.util.Arrays;
import java.util.Collections;

// A FuncInstance is analogous to a stack frame in a normal running system
public class FuncInstance<N extends Func> implements Runnable {
  N design;
  ConnectionInstance [] connections;
  ConnectionInstance [] upstreams;

  public FuncInstance(N design) {
    this.design = design;
    connections = new ConnectionInstance[design.getNumOutputs()];
    upstreams = new ConnectionInstance[design.getNumInputs()];
  }

  public N getFunc() {
    return design;
  }

  public ConnectionInstance getOutput(int output) {
    if (!Func.validPut(output, connections.length)) {
      throw new Func.BadConnectionException();
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
    if (!Func.validPut(output, connections.length)) {
      throw new Func.BadConnectionException();
    }
    connections[output] = ci;
  }

  public ConnectionInstance getInput(int input) {
    if (!Func.validPut(input, upstreams.length)) {
      throw new Func.BadConnectionException();
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
    if (!Func.validPut(input, upstreams.length)) {
      throw new Func.BadConnectionException();
    }
    upstreams[input] = ci;
  }

  public void run() {
  }
}
