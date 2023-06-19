package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ZuvConstantTest {

  @Test
  public void canCreateConstant() {
	ZConstant constant = new ZConstant((Integer)7);
  }

  @Test
  public void canReadConstantOutput() {
	ZConstant constant = new ZConstant((Integer)7);
	assertEquals(7, constant.fetch());
  }
}

