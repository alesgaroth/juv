package com.alesgaroth.zuv.design;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import org.junit.jupiter.api.Test;

import com.alesgaroth.zuv.design.Extensible.Extension;
import com.alesgaroth.zuv.design.Extensible.CloneableExtension;
import com.alesgaroth.zuv.index.Index;

public class FuncTest 
{
    Index ndx = Index.createRoot("test1");

    Func one = new Func(0, 1, ndx);
    Func two = new Func(1, 0, ndx);
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
    public void canAddInputs() {
      one.addInput();
      assertEquals(1, one.getNumInputs());
    }

    @Test
    public void canAddOutputs() {
      two.addOutput();
      assertEquals(1, two.getNumOutputs());
    }

    @Test
    public void canGetOtherEndOfOutput() {
      two.dependOn(0, one, 0);

      Value c = one.getOutput(0); 
      Iterator<Value.FuncPort> it = c.getListeners().iterator();
      assertEquals(it.next(), new Value.FuncPort(two, 0));
    }

    @Test
    public void canClone() {
      Func newf = two.shallowClone();
      assertNotNull(newf);
    }

    @Test
    public void cloneClonesExtensions() {

      Extension ex = new CloneableExtension(){
        public CloneableExtension shallowCopy() { return this; }
      };
      one.extendWith(ex);

      Func newone = one.shallowClone();
      Func newtwo = two.shallowClone();

      assertNotNull(newone);
      assertNotNull(newtwo);
      assertFalse(newtwo == newone);

      assertNotNull(newone.getExtension(ex.getClass()), "should have gotten something from the extension on one");
      assertNull(newtwo.getExtension(ex.getClass()), "shouldn't have gotten anything from the extension on two");
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
