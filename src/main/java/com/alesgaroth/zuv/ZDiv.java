package com.alesgaroth.zuv;

public class ZDiv extends ZNode<Integer> {

  ZValue<Integer> [] inputs = new ZValue[2];

  public ZDiv() {
  	this.value.set((Integer)8);
  }

  void setInput(ZValue v, int input) {
	inputs[input] = v;
	if (inputs[0] != null && inputs[1] != null) {
		this.value.set((Integer)(((int)inputs[0].fetch())/(inputs[1].fetch())));
	}
  }

}
