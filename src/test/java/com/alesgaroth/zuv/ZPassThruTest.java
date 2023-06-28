package com.alesgaroth.zuv;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ZPassThruTest {

  ZVar<Integer> var1;
  ZVar<Integer> var2;

  @BeforeEach public void setUp() {
  	var1 = new ZVar<Integer>(3);
  	var2 = new ZVar<Integer>(5);
  }

  @Test public void canPassThru() {
  	 ZGraphNode<Integer> gn = new ZGraphNode<Integer>(1, 1);
	 gn.setInput(var.output(0), 0);
  }
}
