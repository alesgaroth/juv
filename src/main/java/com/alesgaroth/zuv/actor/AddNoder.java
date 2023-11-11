package com.alesgaroth.zuv.actor;

import com.alesgaroth.zuv.ZGraphNode;
import com.alesgaroth.zuv.ZNode;

public class AddNoder {

   public ZNode addNode(ZGraphNode parent) {
      ZNode n = new ZNode(0, 0);
      parent.addNode(n);
      return n;
   }
}
