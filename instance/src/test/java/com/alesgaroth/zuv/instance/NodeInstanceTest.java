package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import com.alesgaroth.zuv.design.Node;
import java.util.List;
import java.util.Map;

public class NodeInstanceTest {
    @Test
    public void canInstantiate1() {
        Node n = new Node(0, 0);
        NodeInstance ni = new NodeInstance(n);
        assertEquals(ni.getNode(), n);
        assertThrows(Node.BadConnectionException.class, () -> ni.getOutput(-1));
        assertThrows(Node.BadConnectionException.class, () -> ni.getOutput(1));
        assertThrows(Node.BadConnectionException.class, () -> ni.getInput(-1));
        assertThrows(Node.BadConnectionException.class, () -> ni.getInput(1));
        assertThrows(Node.BadConnectionException.class, () -> ni.setInput(null, 1));
        assertThrows(Node.BadConnectionException.class, () -> ni.setInput(null, -1));
    }


    @Test
    public void canGetIterableOfInputs() {
      List<NodeInstance> nodes = simpleGraph();
      NodeInstance node = nodes.get(1);
      for(ConnectionInstance ci: node.getInputs()){
        assertFalse(ci.isReady());
        return;
      }
      assertFalse(true);
    }

    @Test
    public void checkReadyOfNodeInputs() {
      List<NodeInstance> nodes = simpleGraph();
      NodeInstance node = nodes.get(1);
      assertFalse(node.inputsReady());
      ((VariableNodeInstance)nodes.get(0)).update(0);
      assertTrue(node.inputsReady());
    }

   private List<NodeInstance> simpleGraph() {
      Node one = new VariableNode(0, 1);
      Node two = new Node(1, 1);
      two.dependOn(0, one, 0);
      Map<Class<? extends Node>, Class<? extends NodeInstance>> classMap = Map.of(
        VariableNode.class, VariableNodeInstance.class,
        Node.class, NodeInstance.class);
      AlgorithmInstance.InstanceFactory factory = new InstanceMapFactory(classMap);
      return new AlgorithmInstance(factory).instantiate(List.of(one, two));
   }

}
