package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class ZuvConstantTest {
  ZConstant  constant;

  @BeforeEach
  public void setUp(){
    constant = new ZConstant((Integer)7);
  }


  @Test
  public void canReadConstantOutput() {
    assertEquals(7, constant.output(0).fetch(ZQueue.nullQueue));
  }


  @Test // Question:  Maybe we should just ignore nulls?
  public void throwsOnNullListener() {
    assertThrows(NullPointerException.class, () -> constant.addListener(null));
  }

  @Test
  public void canAddListener() {
    ZListener listener = new ZListener() {
      @Override public void valueChanged(ZQueue q) {}
      @Override public void valueInvalidated(ZQueue q) {}
    };
    constant.addListener(listener);
  }
}

