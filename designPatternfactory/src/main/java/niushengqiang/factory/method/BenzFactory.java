package niushengqiang.factory.method;

import niushengqiang.factory.product.Benz;
import niushengqiang.factory.product.Car;

public class BenzFactory implements CarFactory {

    @Override
    public Car getCar() {
        return new Benz();
    }
}
