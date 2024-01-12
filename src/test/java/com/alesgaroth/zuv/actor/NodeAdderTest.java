package com.alesgaroth.zuv.actor;

import java.util.List;

import com.alesgaroth.zuv.ZGraphNode;
import com.alesgaroth.zuv.ZListener;
import com.alesgaroth.zuv.ZNode;
import com.alesgaroth.zuv.ZQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NodeAdderTest implements ZListener {

  boolean receivedNewNodeEvent = false;

  NodeAdder adder;
  ZQueue q;
  ZGraphNode root; 

  @BeforeEach
  void before(){
    adder = new NodeAdder();
    q = new ZQueue();
    root = new ZGraphNode(0, 0);
    root.addChildrenListener(this);
    q.setRoot(root);
  }

  @Test
  void canCreateNode() {
    AddNodeRequest anr = new AddNodeRequest();
    //anr.parent = "/"; // TODO replace the string with a NodeLocator

    // this is a new root,  so it should have no children
    Assertions.assertTrue(root.children().isEmpty());

    adder.add(anr);
    q.enqueue(adder);
    q.runTillEmpty();

    Assertions.assertTrue(receivedNewNodeEvent);
    List<ZNode> children = root.children();
    Assertions.assertFalse(children.isEmpty());
    ZNode newNode = children.get(0);
    Assertions.assertEquals(root, newNode.parent());
  }

  @Test void canCreateNodeUnderNotRoot() {

    // first create a direct child
    AddNodeRequest anr = new AddNodeRequest();

    adder.add(anr);
    q.enqueue(adder);
    q.runTillEmpty();


    var zl = new ZListener() {
      boolean myReceivedNewNodeEvent = false;
      public void valueChanged(ZQueue q) {
        myReceivedNewNodeEvent = true;
      }
      public void valueInvalidated(ZQueue q) {
      }
    };
    ZGraphNode newParent = (ZGraphNode)root.children().get(0);
    newParent.addChildrenListener(zl);

    AddNodeRequest anr2 = new AddNodeRequest(newParent);

    adder.add(anr2);
    q.enqueue(adder);
    q.runTillEmpty();

    Assertions.assertTrue(zl.myReceivedNewNodeEvent);
    List<ZNode> children = newParent.children();
    Assertions.assertFalse(children.isEmpty());
    ZNode newNode = children.get(0);
    Assertions.assertEquals(newParent, newNode.parent());
  }

  @Test
  public void pathMakesSense() {
    AddNodeRequest anr = new AddNodeRequest();

    adder.add(anr);
    q.enqueue(adder);
    q.runTillEmpty();
    ZNode newNode = root.children().get(0);
    Assertions.assertTrue(newNode.getPath().startsWith("/"), "a path should start with /");
    Assertions.assertFalse(newNode.getPath().startsWith("//"), "a path should not start with //");
    Assertions.assertTrue(newNode.getPath().startsWith("/" + newNode.name()), " a path should start with / followed by the name of the node");
  }
  // TODO: tests that we can't add new subnodes to nodes that are not graph nodes
  // TODO: tests that we can't add a node that already has a parent to a node.

  // TODO: tests we can copy a node into a child
  // TODO: Tests we can encapsulate a set of nodes into a new graph node that is child of the current parent of them

  // TODO: Tests that the client can get list of children, and their x,y coords, and other info about them.

  public void valueChanged(ZQueue q) {
    receivedNewNodeEvent = true;
  }
  public void valueInvalidated(ZQueue q) {
  }

}
