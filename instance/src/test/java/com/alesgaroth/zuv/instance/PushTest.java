package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.concurrent.Executor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import com.alesgaroth.zuv.design.Node;
import com.alesgaroth.zuv.design.Connection;

public class PushTest 
{

    VariableNode variable = new VariableNode(0, 1);
    Node two = new PassThroughNode(1, 1);
    Node three = new Node(1, 0);
    VariableNodeInstance variableInstance = null;
    ReceiverNodeInstance threeInstance = null;
    PassThroughNodeInstance twoInstance = null;

    Executor executor = new CurrentThreadExecutor();
    PushConnectorStrategy strat = new PushConnectorStrategy(executor);

    static Map<Class<? extends Node>, Class<? extends NodeInstance>> classMap = Map.of(
        Node.class, ReceiverNodeInstance.class,
        PassThroughNode.class, PassThroughNodeInstance.class,
        VariableNode.class, VariableNodeInstance.class
        );
    AlgorithmInstance.InstanceFactory factory = new InstanceMapFactory(classMap) {
      public ConnectionInstance createConnection(NodeInstance ni, int output) {
        return new ConnectionInstance(ni, strat);
      }
    };


    @BeforeEach
    public void before() {
      two.dependOn(0, variable, 0);
      three.dependOn(0, two, 0);
      List<NodeInstance> list = new AlgorithmInstance(factory).instantiate(List.of(variable, two, three));
      variableInstance = (VariableNodeInstance)list.get(0);
      threeInstance = (ReceiverNodeInstance)list.get(2);
      twoInstance = (PassThroughNodeInstance)list.get(1);
    }

    @Test
    public void canMakeChange() {
      variableInstance.update("new value");
    }

    @Test
    public void changePropagates() {
      variableInstance.update("new value");
      assertEquals(1, twoInstance.runCalled);
      assertEquals(1, threeInstance.runCalled);
      assertEquals("new value", threeInstance.getValue());
      variableInstance.update("other value");
      assertEquals("other value", threeInstance.getValue());
    }

    public class CurrentThreadExecutor implements Executor {
      public void execute(Runnable r) {
        r.run();
      }
    }
}
