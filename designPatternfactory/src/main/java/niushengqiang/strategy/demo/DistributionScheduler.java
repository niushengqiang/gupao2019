package niushengqiang.strategy.demo;


import niushengqiang.strategy.demo.distri.AverageDistribution;
import niushengqiang.strategy.demo.distri.DistributeFans;
import niushengqiang.strategy.demo.distri.LeastDistribution;
import niushengqiang.strategy.demo.distri.RandomDistribute;

import java.util.ArrayList;
import java.util.List;

/**
 * 每个策略模式都应该有一个类似注册中心的地方
 */
public class DistributionScheduler {
    private static final List<DistributeFans> registryCenter=new ArrayList<>();
    static {
        registryCenter.add(new AverageDistribution());
        registryCenter.add(new LeastDistribution());
        registryCenter.add(new RandomDistribute());
    }

    public static DistributeFans getDistrbuteInsance(Integer index){
        if((registryCenter.size()-1)>index){
            return registryCenter.get(index);
        }else{
           return registryCenter.get(0);
        }
    }
}
