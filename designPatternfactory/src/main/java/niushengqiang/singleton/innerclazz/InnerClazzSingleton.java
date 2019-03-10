package niushengqiang.singleton.innerclazz;


public class InnerClazzSingleton {
    private InnerClazzSingleton(){}

    public  static  InnerClazzSingleton getInstance(){
        return InnerClass.ic;
    }

    private static class InnerClass{
        private static InnerClazzSingleton ic=new InnerClazzSingleton();
    }
}
