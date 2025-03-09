package com.alesgaroth.zuv.protocol;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.alesgaroth.zuv.index.Effect;
import com.alesgaroth.zuv.index.OpCRDT;
import com.alesgaroth.zuv.index.Replica;

public class Replicator implements Replica {
  TextSender sender;
  OpCRDT replica;
  Replicator(OpCRDT replica, TextSender ts) {
    sender = ts;
    replica.replicateTo(this);
    this.replica = replica;
  }
  public void changed(Effect eff) {
    sender.send(eff.toString());
  }
  public void receive(String msg) {
    String[] parts = msg.split(" ");
    switch(parts[0]) {
      case "added":
        replica.changed(new OpCRDT.Addition(parts[1], parts[2], Set.of(Arrays.copyOfRange(parts, 3, parts.length))));
    }
  }
}
