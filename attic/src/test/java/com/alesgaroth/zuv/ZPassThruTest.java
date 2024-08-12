package com.alesgaroth.zuv;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ZPassThruTest {

  ZVar var1;
  ZVar var2;
  ZQueue q;

  @BeforeEach public void setUp() {
    var1 = new ZVar(3);
    var2 = new ZVar(5);
    q = new ZQueue();
  }

  static public ZNode createPassThru(ZValue v, ZQueue q) {
    ZGraphNode gn = new ZGraphNode(1, 1);
    gn.setInput(v, 0);
    gn.setReturnValue(gn.parameter(0), 0, q);
    return gn;
  }

   @Test public void canPassThru() {
    ZNode gn  = createPassThru(var1.output(0), q);
    q.enqueue(gn);
    q.runTillEmpty();
    assertEquals(3, gn.output(0).fetch(q) );
  }

  @Test public void canPassUp() {
    ZGraphNode gn = new ZGraphNode(0, 1);
    gn.setReturnValue(var2.output(0), 0, q);
    q.enqueue(gn);
    q.runTillEmpty();
    assertEquals(5, gn.output(0).fetch(q) );    
  }
  
  @Test public void canInvalidateUp() {
    ZGraphNode gn = new ZGraphNode(0, 1);
    gn.setReturnValue(var2.output(0), 0, q);
    var2.invalidate(q);
    assertTrue(gn.output(0).isInvalid());    
  }

  @Test public void graphReturnValsCanOnlyConnectToThingsInThisGraph() {
    ZGraphNode gn = new ZGraphNode(0, 1);
    ZGraphNode gn2 = new ZGraphNode(0, 1);
    gn.addNode(var1, q);
    Assertions.assertThrows(GraphMismatchException.class,
      () -> gn2.setReturnValue(var1.output(0), 0, q));
  }
  
}
