package niushengqiang.strategy.demo.distri;

import java.util.List;
import java.util.Map;

public class AverageDistribution implements DistributeFans{
    @Override
    public Map<String, List<String>> distributeFans() {
        System.out.println("平均分配粉丝");
        return null;
    }
}
