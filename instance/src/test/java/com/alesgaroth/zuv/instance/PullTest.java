package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import com.alesgaroth.zuv.design.Node;

public class PullTest extends StratTestBase {

    @BeforeEach
    public void before() {
      super.before(new PullConnectorStrategy());
    }

    @Test
    public void changePropagates() {
      variableInstance.update("a value");
      assertEquals("a value", threeInstance.getValue());
    }

    @Test
    public void changeCausesDownStreamChanges() {
      variableInstance.update("new value");
      assertEquals("new value", threeInstance.getValue());
      variableInstance.update("other value");
      assertEquals("other value", threeInstance.getValue());
    }

}
