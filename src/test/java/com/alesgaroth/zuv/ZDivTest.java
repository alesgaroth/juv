package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ZDivTest {

  ZDiv<Integer> div;

  @Test public void canCreateDiv() {
    div = new ZDiv<Integer>();
  }
}
