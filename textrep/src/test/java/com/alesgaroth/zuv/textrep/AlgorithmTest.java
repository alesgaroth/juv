package com.alesgaroth.zuv.textrep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.alesgaroth.zuv.design.Func;


public class AlgorithmTest {

  @Test
  public void algoWithTwoNotEquivalentToOne() {
    
    Algorithm algo = twoNodeConnected();
    Algorithm algo2 = new Algorithm();
    assertFalse(algo.equivalentTo(algo2));
  }

  @Test
  public void twoEmptyEquivalent() {
    Algorithm algo = new Algorithm();
    Algorithm algo2 = new Algorithm();
    assertTrue(algo.equivalentTo(algo2));
  }

  @Test
  public void twoSinglesEquivalent() {
    Algorithm algo = singleNode();
    Algorithm algo2 = singleNode();
    assertTrue(algo.equivalentTo(algo2));
  }

  @Test
  public void twoUnconnectedDoublesEquivalent() {
    Algorithm algo = twoUnconnected();
    Algorithm algo2 = twoUnconnected();
    assertTrue(algo.equivalentTo(algo2));
  }

  @Test
  public void twoConnectedDoublesEquivalent() {
    Algorithm algo = twoNodeConnected();
    Algorithm algo2 = twoNodeConnected();
    assertTrue(algo.equivalentTo(algo2));
  }

  @Test
  public void twoDifferentlyConnectedDoublesNotEquivalent() {
    Algorithm algo = twoNodeConnected();
    Algorithm algo2 = twoUnconnected();
    assertFalse(algo.equivalentTo(algo2));
  }

  @Test
  public void twoSameConnectedTriplesEquivalent() {
    Algorithm algo = oneRootTwoLeaves();
    Algorithm algo2 = oneRootTwoLeaves();
    assertTrue(algo.equivalentTo(algo2));
  }

  @Test
  public void twoDifferentlyConnectedTriplesNotEquivalent() {
    Algorithm algo = oneRootTwoLeaves();
    Algorithm algo2 = twoRootOneLeaf();
    assertFalse(algo.equivalentTo(algo2));
  }

  Algorithm twoNodeConnected() {
    Algorithm algo = new Algorithm();
    Func one = new Func(0, 1);
    Func two = new Func(1, 0);
    two.dependOn(0, one, 0);
    algo.add(one);
    algo.add(two);
    return algo;
  }

  Algorithm singleNode() {
    Algorithm algo = new Algorithm();
    Func one = new Func(0, 0);
    algo.add(one);
    return algo;
  }

  Algorithm twoUnconnected() {
    Algorithm algo = new Algorithm();
    Func one = new Func(0, 1);
    algo.add(one);
    algo.add(new Func(1, 0));
    return algo;
  }

  Algorithm oneRootTwoLeaves() {
    Algorithm algo = new Algorithm();
    Func one = new Func(0, 1);
    Func one2 = new Func(1, 0);
    Func one3 = new Func(1, 0);
    one2.dependOn(0, one, 0);
    one3.dependOn(0, one, 0);
    algo.add(one);
    algo.add(one2);
    algo.add(one3);
    return algo;
  }

  Algorithm twoRootOneLeaf() {
    Algorithm algo = new Algorithm();
    Func two = new Func(0, 1);
    Func two2 = new Func(2, 0);
    Func two3 = new Func(0, 1);
    two2.dependOn(0, two, 0);
    two2.dependOn(1, two3, 0);
    algo.add(two);
    algo.add(two2);
    algo.add(two3);
    return algo;
  }
}
