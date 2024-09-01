package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Node;

public class VariableNodeInstance extends NodeInstance {
  public VariableNodeInstance(VariableNode n) {
    super(n);
  }

  void update(Object newValue){
    getOutput(0).update(newValue);
  }
}
