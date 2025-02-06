package com.alesgaroth.zuv.index;

import java.util.stream.Stream;
import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


public class CRDTTest {
  // this is supposed to be a test for a CRDT
  // It should work with any CRDT we might write that is an add/remove set.
  @ParameterizedTest
  @MethodSource("crdts")
  void canAdd(CRDT<String> crdt) {
    crdt.add("/hello/");
    assertTrue(crdt.contains("/hello/"), " actual contents:" + crdt.elements());
  }

  @ParameterizedTest
  @MethodSource("crdts")
  void canRemove(CRDT<String> crdt) {
    crdt.add("/hello/");
    crdt.remove("/hello/");
    assertFalse(crdt.contains("/hello/"), " actual contents:" + crdt.elements() + ((OpCRDT)crdt).queue);
  }

  static Stream<CRDT<String>> crdts() {
    return Stream.of(new OpCRDT<String>("replica0", new ArrayDeque<OpCRDT.Effect>()));
  }
  //
  // first simple things like
  // can add to one
  // can remove from one
  // can add multiple to one
  //
  // then two CRDTs that communicate.
  // Add to one and the other one gets the obj
  // Remove from one and it disappears from the other
  // add to one, remove from the other and it disappears from the first.
  //
  // then concurrent additions/deletions
  // Add to one, wait, simultaneously remove it and add another, wait, and everything works
  //
  // add to one, add same obj to the other
  // remove from one, remove it also from the other
  // add to one while removing from the other (that add wins)
  //
  // concurrent with more than two CRDTs.
  //
}
