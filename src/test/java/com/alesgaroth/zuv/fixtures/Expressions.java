package com.alesgaroth.zuv.fixtures;


import com.alesgaroth.zuv.ZRuntime;

public class Expressions {
    String outputValue;
    public void setExpression(String value) {
        ZRuntime rt = new ZRuntime();
        outputValue = rt.eval(value);
    }

    public String output() {
        return outputValue;
    }
}
