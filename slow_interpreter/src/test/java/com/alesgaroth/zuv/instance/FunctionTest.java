package com.alesgaroth.zuv.instance;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alesgaroth.zuv.design.CalcFunc;
import com.alesgaroth.zuv.design.SimpleCalcFunc;
import com.alesgaroth.zuv.index.Index;

public class FunctionTest {
  Index ndx = Index.createRoot("FunctionTest");
  @Test
  public void simpleFunction() {
      Function<Object,Object> f = a -> -(Integer)a;
      CalcFunc cn = new SimpleCalcFunc(f, ndx);
      Object[] outputs = cn.doCalculation(new Object[]{-3});
      assertEquals(3, outputs[0]);
  }
}
