package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import com.alesgaroth.zuv.design.Func;
import com.alesgaroth.zuv.index.Index;
import java.util.List;
import java.util.Map;

public class FuncInstanceTest {
    Index ndx = Index.createRoot("test");
    @Test
    public void canInstantiate1() {
        Func n = new Func("n", 0, 0, ndx);
        FuncInstance<Func> ni = new FuncInstance(n);
        assertEquals(ni.getFunc(), n);
        assertThrows(Func.BadValueException.class, () -> ni.getOutput(-1));
        assertThrows(Func.BadValueException.class, () -> ni.getOutput(1));
        assertThrows(Func.BadValueException.class, () -> ni.getInput(-1));
        assertThrows(Func.BadValueException.class, () -> ni.getInput(1));
        assertThrows(Func.BadValueException.class, () -> ni.setInput(null, 1));
        assertThrows(Func.BadValueException.class, () -> ni.setInput(null, -1));
    }


    @Test
    public void canGetIterableOfInputs() {
      List<FuncInstance<Func>> funcs = simpleGraph();
      FuncInstance<Func> func = funcs.get(1);
      for(ValueInstance ci: func.getInputs()){
        assertFalse(ci.isReady());
        return;
      }
      assertFalse(true);
    }

    @Test
    public void checkReadyOfFuncInputs() {
      List<FuncInstance<Func>> funcs = simpleGraph();
      FuncInstance<Func> func = funcs.get(1);
      assertFalse(func.inputsReady());
      ((VariableFuncInstance)funcs.get(0)).update(0);
      assertTrue(func.inputsReady());
    }

   private List<FuncInstance<Func>> simpleGraph() {
      Func one = new VariableFunc("one", 0, 1, ndx);
      Func two = new Func("two", 1, 1, ndx);
      two.dependOn(0, one, 0);
      Map<Class<? extends Func>, Class<? extends FuncInstance>> classMap = Map.of(
        VariableFunc.class, VariableFuncInstance.class,
        Func.class, FuncInstance.class);
      AlgorithmInstance.InstanceFactory factory = new InstanceMapFactory(classMap);
      return new AlgorithmInstance(factory).instantiate(List.of(one, two));
   }

}
