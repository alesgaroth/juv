package com.alesagorth.zuv.threelayer;

// layer 1
public abstract class Node {
  int numInBound;
  List<Connection> outBound = new ArrayList<>();

  List<ConnectionType> outBoundTypes = new ArrayList<>();
  List<ConnectionType> inBoundTypes = new ArrayList<>();

  public abstract void doCalculations();

  public int getNumInBound() {
    return numInBound
  }
  public int getNumOutBound() {
    return outBound.size();
  }

  public Connection getOutBound(int i) {
    return outBound.get(i);
  }

  public ConnectionType getOutBoundType(int i) {
    return outBoundTypes.get(i);
  }
  public ConnectionType getInBoundType(int i) {
    return inBoundTypes.get(i);
  }
}
