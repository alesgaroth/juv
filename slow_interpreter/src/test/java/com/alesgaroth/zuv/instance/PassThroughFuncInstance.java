package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Func;

public class PassThroughFuncInstance extends FuncInstance {

  public int runCalled = 0;

  public PassThroughFuncInstance(PassThroughFunc n) {
    super(n);
  }

  @Override
  public void run(){
    runCalled += 1;
    getOutput(0).update(getInput(0).getValue());
  }
}
