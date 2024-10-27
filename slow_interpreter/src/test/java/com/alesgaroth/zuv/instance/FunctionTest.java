package com.alesgaroth.zuv.instance;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alesgaroth.zuv.design.Func;
import com.alesgaroth.zuv.design.CalcFunc;
import com.alesgaroth.zuv.design.SimpleCalcFunc;

public class FunctionTest {
  @Test
  public void simpleFunction() {
      Function<Integer,Integer> f = a -> -a;
      CalcFunc cn = new SimpleCalcFunc(f);
      Object[] outputs = cn.doCalculation(new Object[]{-3});
      assertEquals(3, outputs[0]);
  }
}
