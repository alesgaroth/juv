package com.alesgaroth.zuv.instance;



import java.util.List;
import java.util.Map;

import com.alesgaroth.zuv.design.Func;
import com.alesgaroth.zuv.index.Index;

public abstract class StratTestBase {

    Index ndx = Index.createRoot("strat");
    VariableFunc variable = new VariableFunc(0, 1, ndx);
    Func two = new PassThroughFunc(1, 1, ndx);
    Func three = new Func(1, 0, ndx);
    VariableFuncInstance variableInstance = null;
    ReceiverFuncInstance threeInstance = null;
    PassThroughFuncInstance twoInstance = null;

    ValueInstance.ConnectorStrategy strat;

    static Map<Class<? extends Func>, Class<? extends FuncInstance>> classMap = Map.of(
        Func.class, ReceiverFuncInstance.class,
        PassThroughFunc.class, PassThroughFuncInstance.class,
        VariableFunc.class, VariableFuncInstance.class
        );
    AlgorithmInstance.InstanceFactory factory = new InstanceMapFactory(classMap) {
      public ValueInstance createValue(FuncInstance ni, int output) {
        return new ValueInstance(ni, strat);
      }
    };

    public void before(ValueInstance.ConnectorStrategy strt) {
      this.strat = strt;
      two.dependOn(0, variable, 0);
      three.dependOn(0, two, 0);
      List<FuncInstance<Func>> list = new AlgorithmInstance(factory).instantiate(List.of(variable, two, three));
      variableInstance = (VariableFuncInstance)list.get(0);
      threeInstance = (ReceiverFuncInstance)list.get(2);
      twoInstance = (PassThroughFuncInstance)list.get(1);
    }
}
