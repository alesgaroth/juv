package com.alesgaroth.zuv.index;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CompositeListenerTest {
  public class SpyListener implements CRDT.CRDTListener<String> {
    public void added(String e) {
    }
    public void removed(String e) {
    }
  }
  @Test
  public void canAddListener() {
    CRDT fake = new FakeCRDT();
    CompositeListener cl = new CompositeListener(fake);

    cl.register(new SpyListener(), "/");
  }

}
