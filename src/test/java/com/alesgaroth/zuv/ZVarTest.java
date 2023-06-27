package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ZVarTest {

  ZVar<Integer> var;
  boolean [] listenerCalled;

  @BeforeEach public void setUp() {
    var = new ZVar<Integer>((Integer)7);
  }

  @Test public void canListen(){
	listenerCalled = new boolean[1];
	addListener(0);
	var.set((Integer)8);
	assertTrue(listenerCalled[0]);
  }

  private void addListener(final int num) {
	ZListener<Integer> listener = new ZListener<>(){
	  @Override
	  public void valueChanged(Integer q) {
		listenerCalled[num] = true;
	  }
	};
	var.output(0).addListener(listener);
  }

  @Test public void canHaveMultipleListeners(){
	final int numL = 15;
	listenerCalled = new boolean[numL];
	for(int k = 0; k < numL; k += 1)
	  addListener(k);
	var.set((Integer)8);
	for(int k = 0; k < numL; k += 1)
	  assertTrue(listenerCalled[k]);
  }
}
