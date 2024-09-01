package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Node;

public class ReceiverNodeInstance extends NodeInstance {

  public ReceiverNodeInstance(Node n) {
    super(n);
  }

  public Object getValue() {
    return getInput(0).getValue();
  }

}
