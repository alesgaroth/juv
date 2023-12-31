package com.alesgaroth.zuv;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ZDivTest {

  ZDiv div;
  ZVar var1;
  ZVar var2;

  @Test public void canCreateDiv() {
    div = new ZDiv();
    var1 = new ZVar(32);
    var2 = new ZVar(4);
    div.setInput(var1.output(0), 0);
    div.setInput(var2.output(0), 1);

    assertEquals((Integer)8, div.output(0).fetch(ZQueue.nullQueue));
  }

  @Test
  public void canChangeDiv() {
    div = new ZDiv();
    var1 = new ZVar(40);
    var2 = new ZVar(4);
    div.setInput(var1.output(0), 0);
    div.setInput(var2.output(0), 1);
    div.execute(ZQueue.nullQueue);

    assertEquals((Integer)10, div.output(0).fetch(ZQueue.nullQueue));

    var1.set(32, ZQueue.nullQueue);

    div.execute(ZQueue.nullQueue);
    assertEquals((Integer)8, div.output(0).fetch(ZQueue.nullQueue));
  }
}
