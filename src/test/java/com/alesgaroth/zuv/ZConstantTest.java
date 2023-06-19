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
  public void canCreateConstant() {
  }

  @Test
  public void canReadConstantOutput() {
    assertEquals(7, constant.fetch());
  }


  @Test
  public void throwsOnNullListener() {
    assertThrows(NullPointerException.class, () -> constant.addListener(null));
  }
}

