package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class ZuvConstantTest {
  ZConstant<Integer>  constant;

  @BeforeEach
  public void setUp(){
    constant = new ZConstant<Integer>((Integer)7);
  }


  @Test
  public void canReadConstantOutput() {
    assertEquals(7, constant.output(0).fetch());
  }


  @Test
  public void throwsOnNullListener() {
    assertThrows(NullPointerException.class, () -> constant.addListener(null));
  }

  @Test
  public void canAddListener() {
  	ZListener<Integer> listener = new ZListener<Integer>();
	constant.addListener(listener);
  }

  @Test void canCreateValue() {
  	ZValue<Integer> integer = new ZValue<Integer>(7);
  }
}

