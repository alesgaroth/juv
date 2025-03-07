package com.alesgaroth.zuv.protocol;

import com.alesgaroth.zuv.index.Effect;
import com.alesgaroth.zuv.index.OpCRDT;
import com.alesgaroth.zuv.index.Replica;

public class Replicator implements Replica {
  TextSender sender;
  Replicator(OpCRDT replica, TextSender ts) {
    sender = ts;
    replica.replicateTo(this);
  }
  public void changed(Effect eff) {
    sender.send(eff.toString());
  }
}
