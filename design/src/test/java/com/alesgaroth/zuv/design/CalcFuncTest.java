package com.alesgaroth.zuv.design;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Function;

public class CalcFuncTest 
{
    @Test
    public void canCreateACalcFunc()
    {
      CalcFunc cn = new CalcFunc(1, 1);
      Func n = cn;
      Object [] outputs = cn.doCalculation(new Object[]{n});
      assertNotNull(outputs);
    }

    @Test
    public void simpleFunctionCalcFunc(){
      Function<Integer,Integer> f = a -> -a;
      CalcFunc cn = new SimpleCalcFunc(f);
      Object[] outputs = cn.doCalculation(new Object[]{-3});
      assertEquals(3, outputs[0]);
    }

    @Test
    public void extendSimpleFunctionCalcFunc(){
      Function<Integer,Integer> f = a -> -a;
      CalcFunc cn = new SimpleCalcFunc(f);

      CalcFunc cn2 = (CalcFunc)cn.shallowClone();
      Object[] outputs = cn2.doCalculation(new Object[]{-3});
      assertEquals(3, outputs[0]);
    }
}
