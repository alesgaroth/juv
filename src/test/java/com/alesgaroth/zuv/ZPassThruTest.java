package com.alesgaroth.zuv;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ZPassThruTest {

  ZVar<Integer> var1;
  ZVar<Integer> var2;
  ZQueue q;

  @BeforeEach public void setUp() {
    var1 = new ZVar<Integer>(3);
    var2 = new ZVar<Integer>(5);
    q = new ZQueue();
  }

  static public ZNode<Integer> createPassThru(ZValue<Integer> v) {
    ZGraphNode<Integer> gn = new ZGraphNode<Integer>(1, 1);
	  gn.setInput(v, 0);
    gn.setReturnValue(gn.parameter(0), 0);
    return gn;
  }

  @Disabled
   @Test public void canPassThru() {
    ZNode<Integer> gn  = createPassThru(var1.output(0));
    q.enqueue(gn);
    q.runTillEmpty();
    assertEquals(3, gn.output(0).fetch(q) );
  }

  @Disabled
  @Test public void canPassUp() {
    ZGraphNode<Integer> gn = new ZGraphNode<Integer>(0, 1);
    gn.setReturnValue(var2.output(0), 0);
    q.enqueue(gn);
    q.runTillEmpty();
    assertEquals(5, gn.output(0).fetch(q) );    
  }
  
  @Test public void canInvalidateUp() {
    ZGraphNode<Integer> gn = new ZGraphNode<Integer>(0, 1);
    gn.setReturnValue(var2.output(0), 0);
    var2.invalidate(ZQueue.nullQueue);
    assertTrue(gn.output(0).isInvalid());    
  }

  @Test public void graphReturnValsCanOnlyConnectToThingsInThisGraph() {
    ZGraphNode<Integer> gn = new ZGraphNode<Integer>(0, 1);
    ZGraphNode<Integer> gn2 = new ZGraphNode<Integer>(0, 1);
    gn.addNode(var1);
    Assertions.assertThrows(GraphMismatchException.class,
      () -> gn2.setReturnValue(var1.output(0), 0));
  }
  
}
