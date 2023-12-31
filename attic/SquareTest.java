package com.alesgaroth.zuv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SquareTest {
  ZConstant  constant;

  @BeforeEach void before() {
    constant = new ZConstant((Integer)7);
  }

  @Test void test1() {
    Square square = new Square(constant);
    ZNode sq = square.fetch();
    sq.setInput(constant.output(0), 0);
    sq.execute(ZQueue.nullQueue);
    Assertions.assertEquals(49, sq.output(0).fetch(ZQueue.nullQueue));
  }
}
