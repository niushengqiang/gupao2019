package niushengqiang.factory.abstractfactory;

import niushengqiang.factory.product.Car;

public abstract  class CarAbstractFactory {

    protected void pre(String name){
        System.out.println("生产"+name+"之前进行准备...");
    }
    public abstract Car makeBenz();
    public abstract Car makeRollsRoyce();
    public abstract Car makeLamborghini();

}
