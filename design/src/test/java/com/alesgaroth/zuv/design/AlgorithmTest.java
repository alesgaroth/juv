package com.alesgaroth.zuv.design;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import com.alesgaroth.zuv.index.Index;


public class AlgorithmTest {

  Index ndx = Index.createRoot("AlgorithmTest");
  Index ndx2 = Index.createRoot("AlgorithmTest2");

  @Test
  public void algoWithTwoNotEquivalentToOne() {
    
    Algorithm algo = twoNodeConnected(ndx);
    Algorithm algo2 = new Algorithm(ndx2);
    assertFalse(algo.equivalentTo(algo2));
  }

  @Test
  public void twoEmptyEquivalent() {
    Algorithm algo = new Algorithm(ndx);
    Algorithm algo2 = new Algorithm(ndx2);
    assertTrue(algo.equivalentTo(algo2));
  }

  @Test
  public void twoSinglesEquivalent() {
    Algorithm algo = singleNode(ndx);
    Algorithm algo2 = singleNode(ndx2);
    assertTrue(algo.equivalentTo(algo2));
  }

  @Test
  public void twoUnconnectedDoublesEquivalent() {
    Algorithm algo = twoUnconnected(ndx);
    Algorithm algo2 = twoUnconnected(ndx2);
    assertTrue(algo.equivalentTo(algo2));
  }

  @Test
  public void twoConnectedDoublesEquivalent() {
    Algorithm algo = twoNodeConnected(ndx);
    Algorithm algo2 = twoNodeConnected(ndx2);
    assertTrue(algo.equivalentTo(algo2));
  }

  @Test
  public void twoDifferentlyConnectedDoublesNotEquivalent() {
    Algorithm algo = twoNodeConnected(ndx);
    Algorithm algo2 = twoUnconnected(ndx2);
    assertFalse(algo.equivalentTo(algo2));
  }

  @Test
  public void twoSameConnectedTriplesEquivalent() {
    Algorithm algo = oneRootTwoLeaves(ndx);
    Algorithm algo2 = oneRootTwoLeaves(ndx2);
    assertTrue(algo.equivalentTo(algo2));
  }

  @Test
  public void twoDifferentlyConnectedTriplesNotEquivalent() {
    Algorithm algo = oneRootTwoLeaves(ndx);
    Algorithm algo2 = twoRootOneLeaf(ndx2);
    assertFalse(algo.equivalentTo(algo2));
  }

  @Test public void twoLongEquivalent() {
    same(longAlgo(ndx), longAlgo(ndx2));
  }

  @Test public void twoParallelNotEquivalent() {
    different(spur(ndx, 3), spur(ndx2, 4));
  }

  public void same(Algorithm algo, Algorithm algo2) { 
    assertTrue(algo.equivalentTo(algo2));
  }

  public void different(Algorithm algo, Algorithm algo2) { 
    assertFalse(algo.equivalentTo(algo2));
  }


  Algorithm twoNodeConnected(Index ndx) {
    return CacheMap.twoConnectedNodes(ndx);
  }

  Algorithm singleNode(Index ndx) {
    return CacheMap.oneNode(ndx);
  }

  Algorithm twoUnconnected(Index ndx) {
    Algorithm algo = new Algorithm(ndx);
    Func one = new Func("one", 0, 1, ndx);
    algo.add(one);
    algo.add(new Func("two", 1, 0, ndx));
    return algo;
  }

  Algorithm oneRootTwoLeaves(Index ndx) {
    Algorithm algo = new Algorithm(ndx);
    Func one = new Func("one", 0, 1, ndx);
    Func one2 = new Func("one2", 1, 0, ndx);
    Func one3 = new Func("one3", 1, 0, ndx);
    one2.dependOn(0, one, 0);
    one3.dependOn(0, one, 0);
    algo.add(one);
    algo.add(one2);
    algo.add(one3);
    return algo;
  }

  Algorithm twoRootOneLeaf(Index ndx) {
    Algorithm algo = new Algorithm(ndx);
    Func two = new Func("two", 0, 1, ndx);
    Func two2 = new Func("two2", 2, 0, ndx);
    Func two3 = new Func("two3", 0, 1, ndx);
    two2.dependOn(0, two, 0);
    two2.dependOn(1, two3, 0);
    algo.add(two);
    algo.add(two2);
    algo.add(two3);
    return algo;
  }

  Algorithm longAlgo(Index ndx) {
    Algorithm algo = new Algorithm(ndx);
    Func was = new Func("was", 0, 1, ndx);
    algo.add(was);
    for (int k = 0; k < 10; k += 1) {
      Func next = new Func("next"+k, 1, 1, ndx);
      algo.add(next);
      next.dependOn(0, was, 0);
      was = next;
    }
    Func last = new Func("last", 1, 0, ndx);
    algo.add(last);
    last.dependOn(0, was, 0);
    return algo;
  }

  Algorithm spur(Index ndx, int j) {
    Algorithm algo = new Algorithm(ndx);
    Func was = new Func("was", 0, 1, ndx);
    Func opt = null;
    algo.add(was);
    for (int k = 0; k < 10; k += 1) {
      Func next = new Func("next"+k, 1, 1, ndx);
      algo.add(next);
      next.dependOn(0, was, 0);
      if (j == k ) {
        opt = new Func("opt", 1, 1, ndx);
        algo.add(opt);
        opt.dependOn(0, was, 0);
      }
      was = next;
    }
    Func last = new Func("last", 1, 0, ndx);
    algo.add(last);
    last.dependOn(0, was, 0);
    return algo;
  }

}
