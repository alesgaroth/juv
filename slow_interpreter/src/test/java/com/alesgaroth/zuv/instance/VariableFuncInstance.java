package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Func;

public class VariableFuncInstance extends FuncInstance<Func> {
  public int runCalled = 0;

  public VariableFuncInstance(VariableFunc n) {
    super(n);
  }

  public void run() {
    runCalled += 1;
  }


  void update(Object newValue){
    getOutput(0).update(newValue);
  }
}
