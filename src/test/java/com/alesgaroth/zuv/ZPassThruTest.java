package com.alesgaroth.zuv;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ZPassThruTest {

  ZVar<Integer> var1;
  ZVar<Integer> var2;

  @BeforeEach public void setUp() {
  	var1 = new ZVar<Integer>(3);
  	var2 = new ZVar<Integer>(5);
  }

   @Test public void canPassThru() {
    ZGraphNode<Integer> gn = new ZGraphNode<Integer>(1, 1);
	  gn.setInput(var1.output(0), 0);
    gn.setReturnValue(gn.parameter(0), 0);
    assertEquals(3, gn.output(0).fetch() );
  }

  @Test public void canPassUp() {
    ZGraphNode<Integer> gn = new ZGraphNode<Integer>(0, 1);
    gn.setReturnValue(var2.output(0), 0);
    assertEquals(5, gn.output(0).fetch() );    
  }
  
  @Test public void canInvalidateUp() {
    ZGraphNode<Integer> gn = new ZGraphNode<Integer>(0, 1);
    gn.setReturnValue(var2.output(0), 0);
    var2.invalidate();
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
