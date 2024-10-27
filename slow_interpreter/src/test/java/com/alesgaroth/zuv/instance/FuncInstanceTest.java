package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import com.alesgaroth.zuv.design.Func;
import java.util.List;
import java.util.Map;

public class FuncInstanceTest {
    @Test
    public void canInstantiate1() {
        Func n = new Func(0, 0);
        FuncInstance<Func> ni = new FuncInstance(n);
        assertEquals(ni.getFunc(), n);
        assertThrows(Func.BadConnectionException.class, () -> ni.getOutput(-1));
        assertThrows(Func.BadConnectionException.class, () -> ni.getOutput(1));
        assertThrows(Func.BadConnectionException.class, () -> ni.getInput(-1));
        assertThrows(Func.BadConnectionException.class, () -> ni.getInput(1));
        assertThrows(Func.BadConnectionException.class, () -> ni.setInput(null, 1));
        assertThrows(Func.BadConnectionException.class, () -> ni.setInput(null, -1));
    }


    @Test
    public void canGetIterableOfInputs() {
      List<FuncInstance<Func>> nodes = simpleGraph();
      FuncInstance<Func> node = nodes.get(1);
      for(ConnectionInstance ci: node.getInputs()){
        assertFalse(ci.isReady());
        return;
      }
      assertFalse(true);
    }

    @Test
    public void checkReadyOfFuncInputs() {
      List<FuncInstance<Func>> nodes = simpleGraph();
      FuncInstance<Func> node = nodes.get(1);
      assertFalse(node.inputsReady());
      ((VariableFuncInstance)nodes.get(0)).update(0);
      assertTrue(node.inputsReady());
    }

   private List<FuncInstance<Func>> simpleGraph() {
      Func one = new VariableFunc(0, 1);
      Func two = new Func(1, 1);
      two.dependOn(0, one, 0);
      Map<Class<? extends Func>, Class<? extends FuncInstance>> classMap = Map.of(
        VariableFunc.class, VariableFuncInstance.class,
        Func.class, FuncInstance.class);
      AlgorithmInstance.InstanceFactory factory = new InstanceMapFactory(classMap);
      return new AlgorithmInstance(factory).instantiate(List.of(one, two));
   }

}
