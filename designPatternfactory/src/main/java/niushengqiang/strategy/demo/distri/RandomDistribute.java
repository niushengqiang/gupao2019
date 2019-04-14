package niushengqiang.strategy.demo.distri;

import java.util.List;
import java.util.Map;

/**
 * 随机分配
 */
public class RandomDistribute implements DistributeFans{
    @Override
    public Map<String, List<String>> distributeFans() {
        System.out.println("随机分配粉丝");
        return null;
    }
}
