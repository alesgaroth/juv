package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.alesgaroth.zuv.design.Func;

public class AlgorithmInstanceTest {
    final static Map<Class<? extends Func>, Class<? extends FuncInstance>> mymap = Map.of(Func.class, FuncInstance.class);

    Func one = new Func(0, 1);
    Func two = new Func(1, 0);

    @BeforeEach
    public void before() {
      two.dependOn(0, one, 0);
    }

    @Test void canCreateAMap() {
      AlgorithmInstance instance = new AlgorithmInstance(mymap);
      instance.instantiate(List.of(one, two));
    }


    @Test
    public void canInstantiateAnAlgorithm(){

      List<FuncInstance<Func>> list = new AlgorithmInstance(mymap).instantiate(List.of(one, two));
      FuncInstance oneInstance = list.get(0);
      FuncInstance twoInstance = list.get(1);

      assertNotNull(oneInstance);
      assertNotNull(twoInstance);
      ConnectionInstance ci = oneInstance.getOutput(0);
      assertEquals(ci, twoInstance.getInput(0));
      for(FuncInstance other: ci.getListeners()){
        assertEquals(other.getFunc(), twoInstance.getFunc());
        assertEquals(other, twoInstance);
        return;
      }
      assertFalse(true, "we should have found the other end");
    }
}
