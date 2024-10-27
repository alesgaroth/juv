package com.alesgaroth.zuv.design;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import org.junit.jupiter.api.Test;

public class FuncTest 
{

    Func one = new Func(0, 1);
    Func two = new Func(1, 0);
    @Test
    public void canCreateConnectThem() {
        assertTrue(one != two);
        two.dependOn(0, one, 0);
    }


    @Test
    public void cantConnectToNonExistantOutput() {
      badValue(0, 5);
    }

    @Test
    public void cantConnectToNegativeOutput() {
      badValue(0, -1);
    }

    @Test
    public void cantConnectToNegativeInput() {
      badValue(-1, 0);
    }

    @Test
    public void cantConnectToNonExistantInput() {
      badValue(5, 0);
    }

    @Test
    public void canGetFirstOutput() {
      assertNotNull(one.getOutput(0));
    }

    @Test
    public void cantGetNonExistantOutput() {
      missingOutput(1);
      missingOutput(-1);
    }

    @Test
    public void canGetOtherEndOfOutput() {
      two.dependOn(0, one, 0);

      Value c = one.getOutput(0); 
      Iterator<Value.FuncPort> it = c.getListeners().iterator();
      assertEquals(it.next(), new Value.FuncPort(two, 0));
    }


    void badValue(int input, int output) {
      assertThrows(Func.BadValueException.class,
        () -> two.dependOn(input, one, output));
    }

    void missingOutput(int output) {
      assertThrows(Func.BadValueException.class,
        () -> one.getOutput(output));
    }

}
