package com.alesgaroth.zuv.design;

import java.util.function.Function;

public class SimpleCalcFunc extends CalcFunc {
  final Function func;
  public SimpleCalcFunc(Function f){
    super(1, 1);
    this.func = f;
  }

  public Object[] doCalculation(Object [] inputs) {
    Object [] retval = new Object[1];
    retval[0] = func.apply(inputs[0]);
    return retval;
  }

  @Override
  public Func shallowClone() {
    return new SimpleCalcFunc(this.func).withExtensions(this);
  }

}
