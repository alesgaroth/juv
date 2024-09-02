package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Node;

public class ReceiverNodeInstance extends NodeInstance {
  public int runCalled = 0;

  public ReceiverNodeInstance(Node n) {
    super(n);
  }

  public void run() {
    runCalled += 1;
  }

  public Object getValue() {
    return getInput(0).getValue();
  }

}
