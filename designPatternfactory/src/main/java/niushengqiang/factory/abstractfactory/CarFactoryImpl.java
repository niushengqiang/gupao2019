package niushengqiang.factory.abstractfactory;

import niushengqiang.factory.product.Benz;
import niushengqiang.factory.product.Car;
import niushengqiang.factory.product.Lamborghini;
import niushengqiang.factory.product.RollsRoyce;

/**
 * 这是一个不标准的抽象工厂
 * 这个抽象工厂之中是没有
 *
 *
 */
public class CarFactoryImpl extends  CarAbstractFactory {

    @Override
    public Car makeBenz() {
        this.pre("奔驰");
        return new Benz();
    }

    @Override
    public Car makeRollsRoyce() {
        this.pre("劳斯莱斯");
        return new RollsRoyce();
    }

    @Override
    public Car makeLamborghini() {
       this.pre("兰博基尼");
        return new Lamborghini();
    }
}
