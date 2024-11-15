
package com.alesgaroth.zuv.design;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import com.alesgaroth.zuv.design.Extensible.Extension;

public class ExtensibleTest {
  @Test
  public void testMe() {
    Extensible e = new Extensible();
    Extension ex = new Extension(){};
    e.extendWith(ex);
    assertEquals(ex, e.getExtension(ex.getClass()));
  }
}
