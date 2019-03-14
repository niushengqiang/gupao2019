package niushengqiang.strategy.demo.distri;

import java.util.List;
import java.util.Map;

public class LeastDistribution implements DistributeFans {
    @Override
    public Map<String, List<String>> distributeFans() {
        System.out.println("最少分配策略");
        return null;
    }
}
