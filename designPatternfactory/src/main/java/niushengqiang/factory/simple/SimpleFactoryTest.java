package niushengqiang.factory.simple;

import niushengqiang.factory.product.Car;

/**
 *简单工厂应用demo 
 */
public class SimpleFactoryTest {
    public static void main(String[] args) {
        SimpleFatcory simpleFatcory = new SimpleFatcory();
        Car benz = simpleFatcory.makeCar("Benz");
        System.out.println(benz.getBrand()+"的最高时速为:"+benz.fastestSpeed());
    }
}
