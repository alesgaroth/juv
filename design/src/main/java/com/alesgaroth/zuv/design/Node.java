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
    if (!validConnection(input, upstream, output)) 
      throw new BadConnectionException();

    upstream.outboundConnections[output].addListener(this, input);
  }

  public Connection getOutput(int output) {
    if (!validPut(output, outboundConnections.length)) 
      throw new BadConnectionException();

    return outboundConnections[output];
  }

  boolean validConnection(int input, Node upstream, int output) {
    return validPut(input, numInputs) && validPut(output, upstream.outboundConnections.length);
  }

  boolean validPut(int num, int max) {
    return num >= 0 && max > num;
  }

  public class BadConnectionException extends RuntimeException {
  }
}
