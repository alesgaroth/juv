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
    SimpleCalcFunc f = new SimpleCalcFunc(this.func);
    for (Extensible.Extension ex: getExtensions() ) {
      if (ex instanceof Extensible.CloneableExtension cex) {
        f.extendWith(cex.shallowCopy());
      }
    }
    return f;
  }

}
