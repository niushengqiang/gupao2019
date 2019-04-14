package niushengqiang.factory.simple;

import niushengqiang.factory.product.Benz;
import niushengqiang.factory.product.Car;
import niushengqiang.factory.product.Lamborghini;
import niushengqiang.factory.product.RollsRoyce;

public class SimpleFatcory {

    public Car makeCar(String name){
        if (null!=name&&!name.trim().equals("")) {
            switch (name) {
                case "Benz":
                    return new Benz();
                case "Lamborghini":
                    return new Lamborghini();
                case "RollsRoyce":
                    return new RollsRoyce();
                default:
                    return null;
            }
        }else{
            return null;
        }

    }
}
