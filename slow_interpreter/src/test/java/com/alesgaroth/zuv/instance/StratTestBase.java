package com.alesgaroth.zuv.instance;



import java.util.List;
import java.util.Map;

import com.alesgaroth.zuv.design.Func;

public abstract class StratTestBase {

    VariableFunc variable = new VariableFunc(0, 1);
    Func two = new PassThroughFunc(1, 1);
    Func three = new Func(1, 0);
    VariableFuncInstance variableInstance = null;
    ReceiverFuncInstance threeInstance = null;
    PassThroughFuncInstance twoInstance = null;

    ConnectionInstance.ConnectorStrategy strat;

    static Map<Class<? extends Func>, Class<? extends FuncInstance>> classMap = Map.of(
        Func.class, ReceiverFuncInstance.class,
        PassThroughFunc.class, PassThroughFuncInstance.class,
        VariableFunc.class, VariableFuncInstance.class
        );
    AlgorithmInstance.InstanceFactory factory = new InstanceMapFactory(classMap) {
      public ConnectionInstance createConnection(FuncInstance ni, int output) {
        return new ConnectionInstance(ni, strat);
      }
    };

    public void before(ConnectionInstance.ConnectorStrategy strt) {
      this.strat = strt;
      two.dependOn(0, variable, 0);
      three.dependOn(0, two, 0);
      List<FuncInstance<Func>> list = new AlgorithmInstance(factory).instantiate(List.of(variable, two, three));
      variableInstance = (VariableFuncInstance)list.get(0);
      threeInstance = (ReceiverFuncInstance)list.get(2);
      twoInstance = (PassThroughFuncInstance)list.get(1);
    }
}
