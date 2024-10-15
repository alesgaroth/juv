package com.alesgaroth.zuv.design;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Function;

public class CalcNodeTest 
{
    @Test
    public void canCreateACalcNode()
    {
      CalcNode cn = new CalcNode(1, 1);
      Node n = cn;
      Object [] outputs = cn.doCalculation(new Object[]{n});
      assertNotNull(outputs);
    }

    @Test
    public void simpleFunctionCalcNode(){
      Function<Integer,Integer> f = a -> -a;
      CalcNode cn = new SimpleCalcNode(f);
      Object[] outputs = cn.doCalculation(new Object[]{-3});
      assertEquals(3, outputs[0]);
    }
}
