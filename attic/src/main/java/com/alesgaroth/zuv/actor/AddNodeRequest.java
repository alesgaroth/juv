package com.alesgaroth.zuv.actor;

import com.alesgaroth.zuv.ZNode;

public class AddNodeRequest {
  public String parent;

  public AddNodeRequest() {
    this("/");
  }
  public AddNodeRequest(ZNode parent) {
    this(parent.getPath());
  }

  private AddNodeRequest(String parent) {
    // verify path is valid before making this public
    this.parent = parent;
  }
}
