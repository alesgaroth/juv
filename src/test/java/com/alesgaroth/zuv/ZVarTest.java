package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ZVarTest {

  ZVar var;
  boolean [] listenerCalled;
  boolean [] listenerInvalidated;

  @BeforeEach public void setUp() {
    var = new ZVar((Integer)7);
  }

  @Test public void canListen(){
     listenerCalled = new boolean[1];
     addListener(0);
     var.set((Integer)8, null);
     assertTrue(listenerCalled[0]);
  }

  private void addListener(final int num) {
    ZListener listener = new ZListener(){
      @Override
      public void valueChanged(ZQueue q) {
          listenerCalled[num] = true;
      }
      @Override
      public void valueInvalidated(ZQueue q) {
            listenerInvalidated[num] = true;
      }
    };
    var.output(0).addListener(listener);
  }

  @Test public void canHaveMultipleListeners(){
    final int numL = 15;
    listenerCalled = new boolean[numL];
    for(int k = 0; k < numL; k += 1)
      addListener(k);
    var.set((Integer)8, null);
    for(int k = 0; k < numL; k += 1)
      assertTrue(listenerCalled[k]);
  }

  @Test public void canInvalidate() {
    listenerInvalidated = new boolean[1];
    addListener(0);
    var.invalidate(null);
    assertTrue(listenerInvalidated[0]);
  }
}
