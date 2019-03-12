package niushengqiang.proxy.rebuild;

import java.lang.reflect.Method;
public class HeadHunting implements GPInvocationHandler{
    private Object target;
    public Object getInstance(Object person) throws Exception{
        this.target = person;
        Class<?> clazz = target.getClass();
        return GPProxy.newProxyInstance(new GPClassLoader(),clazz.getInterfaces(),this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object obj = method.invoke(this.target,args);
        ing();
        after();
        return obj;
    }

    private void before(){
        System.out.println("我是猎头,请说出你的需求");
    }
    private void ing(){
        System.out.println("进行查找");
    }
    private void after(){
        System.out.println("OK的话，准备办事");
    }
}
