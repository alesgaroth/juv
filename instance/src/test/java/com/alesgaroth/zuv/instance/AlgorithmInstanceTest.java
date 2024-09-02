package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.alesgaroth.zuv.design.Node;

public class AlgorithmInstanceTest {
    final static Map<Class<? extends Node>, Class<? extends NodeInstance>> mymap = Map.of(Node.class, NodeInstance.class);

    Node one = new Node(0, 1);
    Node two = new Node(1, 0);

    @BeforeEach
    public void before() {
      two.dependOn(0, one, 0);
    }

    @Test void canCreateAMap() {
      AlgorithmInstance instance = new AlgorithmInstance(mymap);
      instance.instantiate(List.of(one, two));
    }


    @Test
    public void canInstantiateAnAlgorithm(){

      List<NodeInstance> list = new AlgorithmInstance(mymap).instantiate(List.of(one, two));
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
