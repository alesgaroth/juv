package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
      Node one = new Node(0, 1);
      Node two = new Node(1, 1);
      two.dependOn(0, one, 0);
      Map<Class<? extends Node>, Class<? extends NodeInstance>> classMap = Map.of(
        Node.class, NodeInstance.class);
      AlgorithmInstance.InstanceFactory factory = new InstanceMapFactory(classMap);
      List<NodeInstance> list = new AlgorithmInstance(factory).instantiate(List.of(one, two));
      NodeInstance twoInstance = list.get(1);
      for(ConnectionInstance ci: twoInstance.getInputs()){
        assertFalse(ci.isReady());
        return;
      }
      assertFalse(true);
    }

}
