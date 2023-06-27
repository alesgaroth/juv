package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ZVarTest {

  boolean listener1Called = false;
  boolean listener2Called = false;

  @Test public void canListen(){
	ZVar<Integer> var;
    var = new ZVar<Integer>((Integer)7);
	ZListener<Integer> listener = new ZListener<>(){
	  @Override
	  public void valueChanged() {
	  listener1Called = true;
	  }
	};
	var.output(0).addListener(listener);
	var.set((Integer)8);
	assertTrue(listener1Called);
  }

  @Test public void canHaveTwoListeners(){
	ZVar<Integer> var;
    var = new ZVar<Integer>((Integer)7);
	ZListener<Integer> listener = new ZListener<>(){
	  @Override
	  public void valueChanged() {
	  listener1Called = true;
	  }
	};
	ZListener<Integer> listener2 = new ZListener<>(){
	  @Override
	  public void valueChanged() {
	  listener2Called = true;
	  }
	};
	var.output(0).addListener(listener);
	var.output(0).addListener(listener2);
	var.set((Integer)8);
	assertTrue(listener2Called);
	assertTrue(listener1Called);
  }
}
