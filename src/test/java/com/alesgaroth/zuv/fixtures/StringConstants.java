package com.alesgaroth.zuv.fixtures;

import com.alesgaroth.zuv.ZConstant;
import com.alesgaroth.zuv.ZQueue;

public class StringConstants {
    

    ZConstant zc;
    public void setConstant(String value) {
      zc = new ZConstant(value);
    }

    public String output() {
        return (String)zc.output(0).fetch(ZQueue.nullQueue);
    }
}