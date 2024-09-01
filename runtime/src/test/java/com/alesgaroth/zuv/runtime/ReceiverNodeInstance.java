package com.alesgaroth.zuv.runtime;


import com.alesgaroth.zuv.design.Node;
import com.alesgaroth.zuv.instance.NodeInstance;

public class ReceiverNodeInstance extends NodeInstance {
  Object receivedValue;

  public ReceiverNodeInstance(Node n) {
    super(n);
  }

  public void run() {
    receivedValue = ((RuntimeConnection)getInput(0)).getValue();
  }

  public Object getValue() {
    return receivedValue;
  }

}
