package niushengqiang.factory.abstractfactory2;

public class LuisDenFurnitureFactory implements FurnitureFactory {
    @Override
    public FurnitureProduct makeBed() {
        return new Bed();
    }

    @Override
    public FurnitureProduct makeDesk() {
        return new Desk();
    }

    @Override
    public FurnitureProduct makeSofa() {
        return new Sofa();
    }
}
