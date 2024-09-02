package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Node;

public class VariableNodeInstance extends NodeInstance {
  public int runCalled = 0;

  public VariableNodeInstance(VariableNode n) {
    super(n);
  }

  public void run() {
    runCalled += 1;
  }


  void update(Object newValue){
    getOutput(0).update(newValue);
  }
}
