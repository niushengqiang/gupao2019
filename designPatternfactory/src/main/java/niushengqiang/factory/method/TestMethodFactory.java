package niushengqiang.factory.method;

import niushengqiang.factory.product.Car;

public class TestMethodFactory {
    public static void main(String[] args) {
        Car car = new BenzFactory().getCar();
        System.out.println(car.getBrand()+"的最高时速为:"+car.fastestSpeed());

        Car car1 = new RollsRoyceFacory().getCar();
        System.out.println(car1.getBrand()+"的最高时速为:"+car1.fastestSpeed());

        Car car2 = new LamborghiniFactory().getCar();
        System.out.println(car2.getBrand()+"的最高时速为:"+car2.fastestSpeed());

    }
}
