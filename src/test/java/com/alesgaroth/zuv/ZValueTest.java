package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ZValueTest {
  @Test void canCreateValue() {
  	ZValue<Integer> i = new ZValue<Integer>(7, new ZNode<Integer>(0, 1));
    Assertions.assertEquals(7, i.fetch(ZQueue.nullQueue));
  }

  @Test void canUpdateValue() {
    ZValue<Integer> i = new ZValue<>(3, new ZNode<Integer>(0, 1));
    i.set(2, null);
    Assertions.assertEquals(2, i.fetch(ZQueue.nullQueue));
  }
}
