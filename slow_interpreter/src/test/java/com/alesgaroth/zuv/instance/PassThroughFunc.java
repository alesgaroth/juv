package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Func;
import com.alesgaroth.zuv.index.Index;

public class PassThroughFunc extends Func {
  public PassThroughFunc(String name, int numInputs, int numOutputs, Index index) {
    super(name, numInputs, numOutputs, index);
  }

}
