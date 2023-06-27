package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ZVarTest {

  boolean listenerCalled = false;

  @Test public void canCreateVar(){
	ZVar<Integer> var;
    var = new ZVar<Integer>((Integer)7);
	ZListener<Integer> listener = new ZListener<>(){
	  @Override
	  public void valueChanged() {
	  listenerCalled = true;
	  }
	};
	var.output(0).addListener(listener);
	var.set((Integer)8);
	assertTrue(listenerCalled);
	
  }
}
