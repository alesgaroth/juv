package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;
import com.alesgaroth.zuv.design.Node;

public class NodeInstanceTest {
    @Test
    public void canInstantiate1() {
        new NodeInstance(new Node(0, 0));
    }

    @Test
    public void canInstantiateAnAlgorithm(){
      Node one = new Node(0, 1);
      Node two = new Node(1, 0);
      NodeInstance oneInstance = null, twoInstance = null;
      two.dependOn(0, one, 0);
      Iterable<NodeInstance> instances = NodeInstance.cloneOutputs(Set.of(one, two));
      for(NodeInstance ni: instances) {
        if(ni.getNode() == one) {
          oneInstance = ni;
        } else if (ni.getNode() == two ) {
          twoInstance = ni;
        }
      }

      assertNotNull(oneInstance);
      assertNotNull(twoInstance);
      ConnectionInstance ci = oneInstance.getOutput(0);
      for(NodeInstance other: ci.getListeners()){
        assertEquals(other.getNode(), twoInstance.getNode());
        assertEquals(other, twoInstance);
        return;
      }
      assertFalse(true, "we should have found the other end");

    }
}
