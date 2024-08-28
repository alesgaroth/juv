package com.alesgaroth.zuv.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import com.alesgaroth.zuv.design.Node;
import com.alesgaroth.zuv.design.Connection;
import com.alesgaroth.zuv.instance.AlgorithmInstance;
import com.alesgaroth.zuv.instance.ConnectionInstance;
import com.alesgaroth.zuv.instance.InstanceMapFactory;
import com.alesgaroth.zuv.instance.NodeInstance;

public class RunTest 
{

    VariableNode variable = new VariableNode(0, 1);
    Node two = new Node(1, 0);
    VariableNodeInstance variableInstance = null;
    NodeInstance twoInstance = null;

    static Map<Class<? extends Node>, Class<? extends NodeInstance>> classMap = Map.of(
        Node.class, NodeInstance.class,
        VariableNode.class, VariableNodeInstance.class
        );
    AlgorithmInstance.InstanceFactory factory = new InstanceMapFactory(classMap) {
      public ConnectionInstance createConnection(Connection output) {
        return super.createConnection(output);
      }
    };


    @BeforeEach
    public void before() {
      two.dependOn(0, variable, 0);
      List<NodeInstance> list = new AlgorithmInstance(factory).instantiate(List.of(variable, two));
      variableInstance = (VariableNodeInstance)list.get(0);
      twoInstance = list.get(1);
    }

    @Test
    public void canMakeChange() {
      variableInstance.update("new value");
    }

    @Test
    public void changePropagates() {
      variableInstance.update("new value");
      new Runner(List.of(variableInstance, twoInstance));
    }

}
