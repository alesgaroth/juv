package com.alesgaroth.zuv.design;

public class Node {
  Connection[] outboundConnections;
  final int numInputs;

  public Node(int numInputs, int numOutputs) {
    this.numInputs = numInputs;
    outboundConnections = new Connection[numOutputs];
    for(int i = 0; i < numOutputs; i += 1) {
      outboundConnections[i] = new Connection();
    }
  }

  public void dependOn(int input, Node upstream, int output) {
    if (!validPut(input, numInputs)) 
      throw new BadConnectionException();

    upstream.getOutput(output).addListener(this, input);
  }

  public int getNumOutputs() {
    return outboundConnections.length;
  }

  public int getNumInputs() {
    return numInputs;
  }

  public Connection getOutput(int output) {
    if (!validPut(output, outboundConnections.length)) 
      throw new BadConnectionException();

    return outboundConnections[output];
  }

  static public boolean validPut(int num, int max) {
    return num >= 0 && max > num;
  }

  static public class BadConnectionException extends RuntimeException {
  }
}
