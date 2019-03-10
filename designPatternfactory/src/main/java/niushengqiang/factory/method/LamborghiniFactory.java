package niushengqiang.factory.method;

import niushengqiang.factory.product.Car;
import niushengqiang.factory.product.Lamborghini;

public class LamborghiniFactory implements CarFactory{
    @Override
    public Car getCar() {
        return new Lamborghini();
    }
}

