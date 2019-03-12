package niushengqiang.proxy.selfproxy;

import niushengqiang.proxy.Person;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 猎头,代理你找工作
 */
public class Headhunting implements InvocationHandler{

    private Person target;

    public Headhunting(Person target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method.invoke(target,args);
        queryInformation();
        interview();
        return null;
    }

    private void queryInformation() {
         System.out.println("查询招聘信息...");
    }

    private void interview() {
        System.out.println("进行面试");
        System.out.println("发放Offer");
    }


}
