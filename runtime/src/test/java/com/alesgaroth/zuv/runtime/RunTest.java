package com.alesgaroth.zuv.design;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import com.alesgaroth.zuv.design.Node;
import com.alesgaroth.zuv.instance.AlgorithmInstance;
import com.alesgaroth.zuv.instance.NodeInstance;

public class RunTest 
{

    Node one = new VariableNode(0, 1);
    Node two = new Node(1, 0);
    NodeInstance oneInstance = null, twoInstance = null;

    @BeforeEach
    public void before() {
      two.dependOn(0, one, 0);
      List<NodeInstance> list = AlgorithmInstance.cloneOutputs(Set.of(one, two));
      oneInstance = list.get(0);
      twoInstance = list.get(1);
    }

    @Test
    public void canMakeChange() {

    }

}
