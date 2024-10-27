package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import com.alesgaroth.zuv.design.Func;

public class ConnectionInstanceTest {

  //If connector receives a value that is equivalent, don't push
  @Test
  public void updateWithSameValueNoStratCall() {
    FuncInstance upstream = null;
    MyConnectorStrategy cs = new MyConnectorStrategy();
    ConnectionInstance ci = new ConnectionInstance(upstream, cs);
    Object value = 7;
    ci.update(value);
    assertEquals(1, cs.updateCalled);
    ci.update(value);
    assertEquals(1, cs.updateCalled);
  }

  @Test
  public void invalidateCallsStrategy() {
    FuncInstance upstream = null;
    MyConnectorStrategy cs = new MyConnectorStrategy();
    ConnectionInstance ci = new ConnectionInstance(upstream, cs);
    Object value = 7;
    ci.update(value);
    assertEquals(1, cs.updateCalled);
    ci.invalidate();
    assertEquals(1, cs.invalidateCalled);
  }

  public class MyConnectorStrategy implements ConnectionInstance.ConnectorStrategy  {
    int updateCalled = 0;
    int invalidateCalled = 0;
    public void update(ConnectionInstance ci) {
      updateCalled += 1;
    }
    public void calcValue(ConnectionInstance ci, FuncInstance upstream) {
    }
    public void invalidate(ConnectionInstance ci, FuncInstance upstream) {
      invalidateCalled += 1;
    }
  }
}
