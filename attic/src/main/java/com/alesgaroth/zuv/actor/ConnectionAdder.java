package com.alesgaroth.zuv.actor;

import com.alesgaroth.zuv.GraphMismatchException;
import com.alesgaroth.zuv.InvalidConnectionException;
import com.alesgaroth.zuv.ZGraphNode;
import com.alesgaroth.zuv.ZNode;

public class ConnectionAdder {

   public void connect(ZNode outputnode, int outputnum, ZNode inputnode, int inputnum) {
     if (outputnode.parent() != inputnode.parent())
        throw new GraphMismatchException();
     throw new InvalidConnectionException();
   }
}
