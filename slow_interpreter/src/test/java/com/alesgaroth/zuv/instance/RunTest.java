package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import com.alesgaroth.zuv.index.Index;
import com.alesgaroth.zuv.design.Func;
import com.alesgaroth.zuv.design.Value;

public class RunTest {

    Index ndx = Index.createRoot("test");
    VariableFunc variable = new VariableFunc("variable", 0, 1, ndx);
    Func two = new Func("two", 1, 0, ndx);
    VariableFuncInstance variableInstance = null;
    ReceiverFuncInstance twoInstance = null;

    static Map<Class<? extends Func>, Class<? extends FuncInstance>> classMap = Map.of(
        Func.class, ReceiverFuncInstance.class,
        VariableFunc.class, VariableFuncInstance.class
        );
    AlgorithmInstance.InstanceFactory factory = new InstanceMapFactory(classMap);


    @BeforeEach
    public void before() {
      two.dependOn(0, variable, 0);
      List<FuncInstance<Func>> list = new AlgorithmInstance(factory).instantiate(List.of(variable, two));
      variableInstance = (VariableFuncInstance)list.get(0);
      twoInstance = (ReceiverFuncInstance)list.get(1);
    }

    @Test
    public void changePropagates() {
      variableInstance.update("new value");
      assertEquals("new value", twoInstance.getValue());
      variableInstance.update("other value");
      assertEquals("other value", twoInstance.getValue());
    }

    @Test
    public void readingBeforeWritingThrows() {
      assertThrows(IllegalStateException.class, () -> twoInstance.getValue());
    }

}
