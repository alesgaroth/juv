package com.alesgaroth.zuv.threelayer;

public class ThreeLayer {

  Executor executor;

  public void run() {
    readConfig();
    setupInputs();
    setupOutputs();
    executor.run();
  }

  void setupInputs() {
    
  }

  void setupOutputs() {

  }

  void readConfig() {
    buildDag();
  }
}
