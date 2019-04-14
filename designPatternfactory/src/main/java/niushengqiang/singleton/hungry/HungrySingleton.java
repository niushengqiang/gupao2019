package niushengqiang.singleton.hungry;

//饿汉式的单例
public class HungrySingleton {
    private HungrySingleton (){}
    private static  final HungrySingleton hs=new HungrySingleton();
    public static HungrySingleton getInstance(){
        return hs;
    }
}
