package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import com.alesgaroth.zuv.design.Node;

public class PullTest 
{

    VariableNode variable = new VariableNode(0, 1);
    Node two = new PassThroughNode(1, 1);
    Node three = new Node(1, 0);
    VariableNodeInstance variableInstance = null;
    ReceiverNodeInstance threeInstance = null;
    PullConnectorStrategy strat = new PullConnectorStrategy();

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
    public void changePropagates() {
      variableInstance.update("a value");
      assertEquals("a value", threeInstance.getValue());
    }

    @Test
    public void changeCausesDownStreamChanges() {
      variableInstance.update("new value");
      assertEquals("new value", threeInstance.getValue());
      variableInstance.update("other value");
      assertEquals("other value", threeInstance.getValue());
    }

}
