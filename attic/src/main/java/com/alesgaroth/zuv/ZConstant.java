package com.alesgaroth.zuv;

public class ZConstant extends ZNode {

  public ZConstant(Object value) {
        super(0, 1);
        output(0).set(value, null);
  }

  public Object outputValue() {
    return output(0).value;
  }

  @Override
  public int hashCode() {
    return outputValue().hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof ZConstant that) {
      Object value  = this.outputValue();
      Object thatValue = that.outputValue();
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
    Object value = this.outputValue();
    return "ZConstant<" + value + ":" + value.getClass().getName()+ ">";
  }

}
