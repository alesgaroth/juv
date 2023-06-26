package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ZValueTest {
  @Test void canCreateValue() {
  	ZValue<Integer> integer = new ZValue<Integer>(7);
  }
}
