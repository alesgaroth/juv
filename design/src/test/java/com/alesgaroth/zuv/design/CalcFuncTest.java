package com.alesgaroth.zuv.design;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Function;
import com.alesgaroth.zuv.index.Index;

public class CalcFuncTest 
{
    Index ndx = Index.createRoot("CalcFunc");
    static Function<Object,Object> f = a -> -(Integer)a;
    @Test
    public void canCreateACalcFunc()
    {
      CalcFunc cn = new CalcFunc("calc", 1, 1, ndx);
      Func n = cn;
      Object [] outputs = cn.doCalculation(new Object[]{n});
      assertNotNull(outputs);
    }

    @Test
    public void simpleFunctionCalcFunc(){
      CalcFunc cn = new SimpleCalcFunc(f, ndx);
      Object[] outputs = cn.doCalculation(new Object[]{-3});
      assertEquals(3, outputs[0]);
    }

    @Test
    public void extendSimpleFunctionCalcFunc(){
      CalcFunc cn = new SimpleCalcFunc(f, ndx);

      CalcFunc cn2 = (CalcFunc)cn.shallowClone();
      Object[] outputs = cn2.doCalculation(new Object[]{-3});
      assertEquals(3, outputs[0]);
    }
}
