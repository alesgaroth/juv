package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Node;

public class PassThroughNodeInstance extends NodeInstance {

  public int runCalled = 0;

  public PassThroughNodeInstance(PassThroughNode n) {
    super(n);
  }

  @Override
  public void run(){
    runCalled += 1;
    getOutput(0).update(getInput(0).getValue());
  }
}
