package com.alesgaroth.zuv.fixtures;

import com.alesgaroth.zuv.ZConstant;
import com.alesgaroth.zuv.ZQueue;

public class IntegerConstants {
    ZConstant zc;
    public void setConstant(Integer value) {
      zc = new ZConstant(value);
    }

    public int output() {
        return (int)zc.output(0).fetch(ZQueue.nullQueue);
    }
}