package com.alesgaroth.zuv.instance;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alesgaroth.zuv.design.Node;
import com.alesgaroth.zuv.design.CalcNode;
import com.alesgaroth.zuv.design.SimpleCalcNode;

public class FunctionTest {
  @Test
  public void simpleFunction() {
      Function<Integer,Integer> f = a -> -a;
      CalcNode cn = new SimpleCalcNode(f);
      Object[] outputs = cn.doCalculation(new Object[]{-3});
      assertEquals(3, outputs[0]);
  }
}
