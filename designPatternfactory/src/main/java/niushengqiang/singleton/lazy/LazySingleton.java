package niushengqiang.singleton.lazy;


//懒汉式加线程安全
public class LazySingleton {
    private LazySingleton(){};
    private static  LazySingleton ls=null;
    public  static LazySingleton getInstace(){
        if(ls==null){
            synchronized (LazySingleton.class){
                if(ls==null){
                    ls=new LazySingleton();
                }else{
                    //nothing todo
                }
            }
        }
        return ls;
    }
}
