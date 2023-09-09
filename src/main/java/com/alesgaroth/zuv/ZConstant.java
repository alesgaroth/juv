package com.alesgaroth.zuv;

public class ZConstant extends ZNode {

  public ZConstant(Object value) {
        super(0, 1);
        output(0).set(value, null);
  }

  @Override
  public int hashCode() {
    return output(0).value.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof ZConstant that) {
      Object value  = this.output(0).value;
      Object thatValue = that.output(0).value;
      if (value != null) {
        return value.equals(thatValue);
      } else {
        return value == thatValue;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    Object value = this.output(0).value;
    return "ZConstant<" + value + ":" + value.getClass().getName()+ ">";
  }

}
