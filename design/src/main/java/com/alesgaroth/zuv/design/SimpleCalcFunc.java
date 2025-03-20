package com.alesgaroth.zuv.design;

import java.util.function.Function;
import com.alesgaroth.zuv.index.Index;

public class SimpleCalcFunc extends CalcFunc {
  final Function<Object, Object> func;
  public SimpleCalcFunc(Function<Object, Object> f, Index parent){
    super(f.toString(), 1, 1, parent);
    this.func = f;
  }

  public Object[] doCalculation(Object [] inputs) {
    Object [] retval = new Object[1];
    retval[0] = func.apply(inputs[0]);
    return retval;
  }

  @Override
  public Func shallowClone() {
    return new SimpleCalcFunc(this.func, parent).withExtensions(this);
  }

}
