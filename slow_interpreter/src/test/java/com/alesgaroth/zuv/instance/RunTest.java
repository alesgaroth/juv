package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import com.alesgaroth.zuv.design.Node;
import com.alesgaroth.zuv.design.Connection;

public class RunTest {

    VariableNode variable = new VariableNode(0, 1);
    Node two = new Node(1, 0);
    VariableNodeInstance variableInstance = null;
    ReceiverNodeInstance twoInstance = null;

    static Map<Class<? extends Node>, Class<? extends NodeInstance>> classMap = Map.of(
        Node.class, ReceiverNodeInstance.class,
        VariableNode.class, VariableNodeInstance.class
        );
    AlgorithmInstance.InstanceFactory factory = new InstanceMapFactory(classMap);


    @BeforeEach
    public void before() {
      two.dependOn(0, variable, 0);
      List<NodeInstance<Node>> list = new AlgorithmInstance(factory).instantiate(List.of(variable, two));
      variableInstance = (VariableNodeInstance)list.get(0);
      twoInstance = (ReceiverNodeInstance)list.get(1);
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
