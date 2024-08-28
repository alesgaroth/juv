
package com.alesgaroth.zuv.runtime;

import com.alesgaroth.zuv.design.Connection;
import com.alesgaroth.zuv.design.Node;
import com.alesgaroth.zuv.instance.ConnectionInstance;
import com.alesgaroth.zuv.instance.NodeInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RuntimeConnection extends ConnectionInstance {

  NodeInstance upstream;

  RuntimeConnection(NodeInstance upstream) {
    this.upstream = upstream;
  }
}

