package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ZDivTest {

  ZDiv div;
  ZVar<Integer> var1;
  ZVar<Integer> var2;

  @Test public void canCreateDiv() {
    div = new ZDiv();
	var1 = new ZVar<Integer>(32);
	var2 = new ZVar<Integer>(4);
	div.setInput(var1.output(0), 0);
	div.setInput(var2.output(0), 1);

	assertEquals((Integer)8, div.output(0).fetch());
  }

  @Test public void canChangeDiv() {
    div = new ZDiv();
	var1 = new ZVar<Integer>(40);
	var2 = new ZVar<Integer>(4);
	div.setInput(var1.output(0), 0);
	div.setInput(var2.output(0), 1);

	assertEquals((Integer)10, div.output(0).fetch());
  }
}
