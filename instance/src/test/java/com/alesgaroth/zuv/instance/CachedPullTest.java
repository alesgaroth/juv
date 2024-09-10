package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Executor;

import com.alesgaroth.zuv.design.Node;

public class CachedPullTest 
{

    VariableNode variable = new VariableNode(0, 1);
    Node two = new PassThroughNode(1, 1);
    Node three = new Node(1, 0);
    VariableNodeInstance variableInstance = null;
    ReceiverNodeInstance threeInstance = null;
    CurrentThreadDelayedExecutor executor = new CurrentThreadDelayedExecutor();
    CachedPullConnectorStrategy strat = new CachedPullConnectorStrategy(executor);

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
    }

    @Test
    public void changeCausesDownStreamChangesEventually() {
      variableInstance.update("new value");
      executor.runItNow();
      assertFalse(threeInstance.getInput(0).isReady());
      assertEquals("new value", threeInstance.getValue());
      variableInstance.update("other value");
      assertTrue(threeInstance.getInput(0).isReady()); // the executor shouldn't have run yet
      executor.runItNow();
      assertFalse(threeInstance.getInput(0).isReady());
      assertEquals("other value", threeInstance.getValue());
      assertTrue(threeInstance.getInput(0).isReady());
    }

    @Test
    public void gettingValueOnlyChangesIfDataChanges() {
      variableInstance.update("new value");
      executor.runItNow();
      assertFalse(threeInstance.getInput(0).isReady());
      assertEquals("new value", threeInstance.getValue());
      executor.runItNow();
      int numCalls = variableInstance.runCalled;
      executor.runItNow();
      assertEquals("new value", threeInstance.getValue());
      executor.runItNow();
      assertEquals(numCalls, variableInstance.runCalled);
      executor.runItNow();
    }

    public class CurrentThreadDelayedExecutor implements Executor {
      Queue<Runnable> delayed = new LinkedList<>();
      public void execute(Runnable r) {
        delayed.offer(r);
      }
      public void runItNow() {
        Runnable r;
        while((r = delayed.poll()) != null) {
          r.run();
        }
      }
    }
}
