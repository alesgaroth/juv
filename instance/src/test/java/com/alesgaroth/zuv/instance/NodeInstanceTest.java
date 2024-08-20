package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import com.alesgaroth.zuv.design.Node;

public class NodeInstanceTest {
    @Test
    public void canInstantiate1() {
        Node n = new Node(0, 0);
        NodeInstance ni = new NodeInstance(n);
        assertEquals(ni.getNode(), n);
        assertThrows(IndexOutOfBoundsException.class, () -> ni.getOutput(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> ni.getOutput(1));
        assertThrows(IndexOutOfBoundsException.class, () -> ni.getInput(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> ni.getInput(1));
        assertThrows(IndexOutOfBoundsException.class, () -> ni.setInput(null, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> ni.setInput(null, -1));
    }

    @Test
    public void canInstantiateAnAlgorithm(){
      Node one = new Node(0, 1);
      Node two = new Node(1, 0);
      two.dependOn(0, one, 0);

      List<NodeInstance> list = AlgorithmInstance.cloneOutputs(Set.of(one, two));
      NodeInstance oneInstance = list.get(0);
      NodeInstance twoInstance = list.get(1);

      assertNotNull(oneInstance);
      assertNotNull(twoInstance);
      ConnectionInstance ci = oneInstance.getOutput(0);
      assertEquals(ci, twoInstance.getInput(0));
      for(NodeInstance other: ci.getListeners()){
        assertEquals(other.getNode(), twoInstance.getNode());
        assertEquals(other, twoInstance);
        return;
      }
      assertFalse(true, "we should have found the other end");
    }
}
