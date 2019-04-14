package niushengqiang.factory.abstractfactory;

import niushengqiang.factory.product.Car;

public class AbstractFactoryTest {
    public static void main(String[] args) {
        CarFactoryImpl carFactory = new CarFactoryImpl();
        Car benz = carFactory.makeBenz();

        System.out.println(benz.getBrand()+"的最高时速为:"+benz.fastestSpeed());
        Car lamborghini = carFactory.makeLamborghini();
        System.out.println(lamborghini.getBrand()+"的最高时速为:"+lamborghini.fastestSpeed());

        Car rollsRoyce = carFactory.makeRollsRoyce();
        System.out.println(rollsRoyce.getBrand()+"的最高时速为:"+rollsRoyce.fastestSpeed());

    }
}
