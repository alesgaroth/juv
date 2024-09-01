package com.alesgaroth.zuv.runtime;


import com.alesgaroth.zuv.design.Node;
import com.alesgaroth.zuv.instance.NodeInstance;

public class VariableNodeInstance extends NodeInstance {
  public VariableNodeInstance(VariableNode n) {
    super(n);
  }

  void update(Object newValue){
    ((RuntimeConnection)getOutput(0)).update(newValue);
  }
}
