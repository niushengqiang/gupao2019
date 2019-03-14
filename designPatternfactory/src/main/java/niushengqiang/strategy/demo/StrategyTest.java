package niushengqiang.strategy.demo;

public class StrategyTest {

    public static void main(String[] args) {
        Integer index=1;
        //N行业务代码
        //..
        //进行粉丝分配
        DistributionScheduler.getDistrbuteInsance(index).distributeFans();
    }
}
