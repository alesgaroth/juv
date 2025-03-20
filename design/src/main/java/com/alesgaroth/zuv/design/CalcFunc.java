package com.alesgaroth.zuv.design;

import com.alesgaroth.zuv.index.Index;

public class CalcFunc extends Func {
  public CalcFunc(String name, int inputs, int outputs, Index parent){
    super(name, inputs, outputs, parent);
  }
  public Object[] doCalculation(Object [] inputs) {
    return new Object[0];
  }
}
