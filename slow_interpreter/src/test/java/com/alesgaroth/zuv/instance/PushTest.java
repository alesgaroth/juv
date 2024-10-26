package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.Executor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import com.alesgaroth.zuv.design.Node;

public class PushTest  extends StratTestBase {

    @BeforeEach
    public void before() {
      Executor executor = new CurrentThreadExecutor();
      super.before(new PushConnectorStrategy(executor));
    }

    @Test
    public void canMakeChange() {
      variableInstance.update("new value");
    }

    @Test
    public void changePropagates() {
      variableInstance.update("new value");
      assertEquals(1, twoInstance.runCalled);
      assertEquals(1, threeInstance.runCalled);
      assertEquals("new value", threeInstance.getValue());
      variableInstance.update("other value");
      assertEquals(2, twoInstance.runCalled);
      assertEquals(2, threeInstance.runCalled);
      assertEquals("other value", threeInstance.getValue());
    }

    public class CurrentThreadExecutor implements Executor {
      public void execute(Runnable r) {
        r.run();
      }
    }
}
