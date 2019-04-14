package niushengqiang.factory.method;

import niushengqiang.factory.product.Car;
import niushengqiang.factory.product.RollsRoyce;

public class RollsRoyceFacory implements CarFactory{
    @Override
    public Car getCar() {
        return new RollsRoyce();
    }
}
