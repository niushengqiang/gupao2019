package niushengqiang.factory.abstractfactory2;

public class AbstractFactory2Test {
    public static void main(String[] args) {
        //双叶家具厂生产的都是双叶家具
        TwoLeafFurnitureFactory twoLeafFurnitureFactory = new TwoLeafFurnitureFactory();
        FurnitureProduct twoLeafBed = twoLeafFurnitureFactory.makeBed();
        FurnitureProduct twoLeafDesk = twoLeafFurnitureFactory.makeDesk();
        FurnitureProduct twoLeafSofa = twoLeafFurnitureFactory.makeSofa();
        System.out.println(twoLeafBed.put());       
        System.out.println(twoLeafDesk.put());
        System.out.println(twoLeafSofa.put());


        LuisDenFurnitureFactory luisDenFurnitureFactory = new LuisDenFurnitureFactory();
        FurnitureProduct luisDenBed = luisDenFurnitureFactory.makeBed();
        FurnitureProduct luisDenDesk = luisDenFurnitureFactory.makeDesk();
        FurnitureProduct luisDenSofa = luisDenFurnitureFactory.makeSofa();
        System.out.println(luisDenBed.put());
        System.out.println(luisDenDesk.put());
        System.out.println(luisDenSofa.put());

    }
}
