package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import com.alesgaroth.zuv.design.Node;
import com.alesgaroth.zuv.design.Connection;

public class RunTest 
{

    VariableNode variable = new VariableNode(0, 1);
    Node two = new Node(1, 0);
    VariableNodeInstance variableInstance = null;
    ReceiverNodeInstance twoInstance = null;
    Executor executor = new CurrentThreadExecutor();

    static Map<Class<? extends Node>, Class<? extends NodeInstance>> classMap = Map.of(
        Node.class, ReceiverNodeInstance.class,
        VariableNode.class, VariableNodeInstance.class
        );
    AlgorithmInstance.InstanceFactory factory = new InstanceMapFactory(classMap);


    @BeforeEach
    public void before() {
      two.dependOn(0, variable, 0);
      List<NodeInstance> list = new AlgorithmInstance(factory).instantiate(List.of(variable, two));
      variableInstance = (VariableNodeInstance)list.get(0);
      twoInstance = (ReceiverNodeInstance)list.get(1);
    }

    @Test
    public void canMakeChange() {
      variableInstance.update("new value");
    }

    @Test
    public void changePropagates() {
      // this works because we're using a CurrentThreadExecutor
      variableInstance.update("new value");
      assertEquals("new value", twoInstance.getValue());
      variableInstance.update("other value");
      assertEquals("other value", twoInstance.getValue());
    }

    public class CurrentThreadExecutor implements Executor {
      public void execute(Runnable r) {
        r.run();
      }
    }

}
