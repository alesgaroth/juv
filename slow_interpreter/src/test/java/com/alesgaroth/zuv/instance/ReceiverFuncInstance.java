package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Func;

public class ReceiverFuncInstance extends FuncInstance {
  public int runCalled = 0;

  public ReceiverFuncInstance(Func n) {
    super(n);
  }

  public void run() {
    runCalled += 1;
  }

  public Object getValue() {
    return getInput(0).getValue();
  }

}
