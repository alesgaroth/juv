package com.alesgaroth.zuv.actor;

import com.alesgaroth.zuv.ZGraphNode;
import com.alesgaroth.zuv.ZNode;
import com.alesgaroth.zuv.ZPathException;
import com.alesgaroth.zuv.ZQueue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NodeAdder implements ZQueue.Executable {
  List<AddNodeRequest> requests = new ArrayList<>();

  public ZNode addNode(ZGraphNode parent, ZQueue q) {
    ZNode n = new ZNode(0, 0);
    parent.addNode(n, q);
    return n;
  }
  public void add(AddNodeRequest req) {
    requests.add(req);
  }

  public void execute(ZQueue q) {
    while (!requests.isEmpty()) {
      AddNodeRequest req = requests.remove(0);
      ZGraphNode parent = followPath(q.getRoot(), req.parent);
      parent.addNewNode(q);
    }
  }

  private ZGraphNode followPath(ZGraphNode start, String path) {
    if (path == "/") return start;
    for (String name : path.split("/")) {
      if (name.length() < 1) continue;
      ZNode node = start.getByName(name);
      if (node == null) {
        throw new ZPathException(path + " is not a valid path");
      } else if (node instanceof ZGraphNode gn) {
        start = gn;
      } else if (path.endsWith("/")) {
        throw new ZPathException(path + " is not a valid path for a non graph node");
      } else if (path.endsWith(name)) {
        return (ZGraphNode)node; // this will fail
      }
    }
    return start;
  }
}
