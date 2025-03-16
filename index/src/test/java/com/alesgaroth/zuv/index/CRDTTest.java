package com.alesgaroth.zuv.index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


public class CRDTTest {
  @ParameterizedTest
  @MethodSource("crdts")
  @Retention(RetentionPolicy.RUNTIME)
  private @interface TestOne {
  }
  @ParameterizedTest
  @MethodSource("doubleCrdts")
  @Retention(RetentionPolicy.RUNTIME)
  private @interface TestTwo {
  }

  Replica rep;
  ArrayList<Effect> log = new ArrayList<>();

  @BeforeEach
  public void before() {
    rep = new Replica(){
      public void changed(Effect effect) {
        log.add(effect);
      }
    };
  }

  static List<CRDT> crdts() {
    return List.of(new OpCRDT("replica0"));
  }

  static List<List<CRDT>> doubleCrdts() {
    return List.of(List.of( new OpCRDT("replica0"),
        new OpCRDT("replica1") ));
  }

  void assertContains(CRDT c, String s) {
    assertTrue(c.contains(s), " actual contents:" + c.elements());
  }

  void assertNotContains(CRDT c, String s) {
    assertFalse(c.contains(s), " actual contents:" + c.elements() + " log: " + log);
  }

  void assertContains(Collection<String> c, String s) {
    assertTrue(c.contains(s), " actual contents:" + c);
  }


  // this is supposed to be a test for a CRDT
  // It should work with any CRDT we might write that is an add/remove set.
  @TestOne
  void canAdd(CRDT crdt) {
    crdt.add("/hello/");
    assertContains(crdt, "/hello/");
  }

  @TestOne
  void canRemove(CRDT crdt) {
    ((OpCRDT)crdt).replicateTo(rep);
    crdt.add("/hello/");
    crdt.remove("/hello/");
    assertNotContains(crdt, "/hello/");
  }


  @TestOne
  void canAddMultiple(CRDT crdt) {
    crdt.add("/foo/");
    crdt.add("/bar/");
    assertContains(crdt, "/foo/");
    assertContains(crdt, "/bar/");
  }

  @TestOne
  void reportsChanges(CRDT crdt) {
      CompositeListenerTest.SpyListener spy = new CompositeListenerTest.SpyListener();
      crdt.setListener(spy);
      crdt.add("/foo/");
      crdt.remove("/foo/");
      assertContains(spy.log, "added /foo/");
      assertContains(spy.log, "removed /foo/");
  }

  @TestTwo
  void canBuildTwo(List<CRDT> crdts) {
    CRDT rep0 = crdts.get(0);
    CRDT rep1 = crdts.get(1);
    ((OpCRDT)rep0).replicateTo((OpCRDT)rep1);
    rep0.add("/foo/");
    rep0.add("/bar/");
    assertContains(rep1, "/foo/");
    assertContains(rep1, "/bar/");
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
