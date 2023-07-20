package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ZValueTest {
  @Test void canCreateValue() {
  	ZValue<Integer> i = new ZValue<Integer>(7, null);
    Assertions.assertEquals(7, i.fetch());
  }

  @Test void canUpdateValue() {
    ZValue<Integer> i = new ZValue<>(3, null);
    i.set(2);
    Assertions.assertEquals(2, i.fetch());
  }
}
