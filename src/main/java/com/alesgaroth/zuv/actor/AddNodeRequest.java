package com.alesgaroth.zuv.actor;

import com.alesgaroth.zuv.ZNode;

public class AddNodeRequest {
  public String parent;

  public AddNodeRequest() {
    this.parent = "/";
  }
  public AddNodeRequest(ZNode parent) {
    this.parent = parent.getPath();
  }
}
