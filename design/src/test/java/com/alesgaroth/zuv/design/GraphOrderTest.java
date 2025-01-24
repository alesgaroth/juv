package com.alesgaroth.zuv.design;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.alesgaroth.zuv.design.Func;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class GraphOrderTest {

  @Test
  public void canOrderEmptyGraph() {
    Algorithm algo = new Algorithm();
    List<Set<Func>> list = new GraphOrder(algo).ordered();
    assertNotNull(list);
    assertEquals(0, list.size());
  }

  @Test
  public void canOrderSingleton() {
    Algorithm algo = new Algorithm();
    algo.add(new Func(0, 0));
    List<Set<Func>> list = new GraphOrder(algo).ordered();
    assertEquals(1, list.size());
  }

  @Test
  public void canOrderTriple() {
    Algorithm algo = new Algorithm();
    Func root = new Func(0, 1);
    Func leaf = new Func(1, 0);
    Func leaf2 = new Func(1, 0);
    leaf.dependOn(0, root, 0);
    leaf2.dependOn(0, root, 0);
    algo.add(root);
    algo.add(leaf);
    algo.add(leaf2);

    List<Set<Func>> list = new GraphOrder(algo).ordered();

    assertEquals(2, list.size());
    assertEquals(1, list.get(0).size());
    assertEquals(2, list.get(1).size());
    assertEquals(root, list.get(0).iterator().next());
    Iterator<Func> it = list.get(1).iterator();
    Func other1 = it.next();
    if (other1 == leaf) {
      assertEquals(leaf2, it.next());
    } else {
      assertEquals(leaf2, other1);
      assertEquals(leaf, it.next());
    }
  }

}
