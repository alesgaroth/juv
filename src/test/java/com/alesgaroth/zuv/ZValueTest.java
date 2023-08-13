package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ZValueTest {
  @Test void canCreateValue() {
  	ZValue i = new ZValue(7, new ZNode(0, 1));
    Assertions.assertEquals(7, i.fetch(ZQueue.nullQueue));
  }

  @Test void canUpdateValue() {
    ZValue i = new ZValue(3, new ZNode(0, 1));
    i.set(2, null);
    Assertions.assertEquals(2, i.fetch(ZQueue.nullQueue));
  }
}
