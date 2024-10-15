package com.alesgaroth.zuv.design;

public class CalcNode extends Node {
  public CalcNode(int inputs, int outputs){
    super(inputs, outputs);
  }
  public Object[] doCalculation(Object [] inputs) {
    return new Object[0];
  }
}
