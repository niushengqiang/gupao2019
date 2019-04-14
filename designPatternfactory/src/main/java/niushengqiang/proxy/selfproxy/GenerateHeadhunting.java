package niushengqiang.proxy.selfproxy;


import niushengqiang.proxy.Person;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这里采用工厂模式
 * 生成代理类
 *
 *
 * 这里对jdk的动态代理进行封装是为了
 * 解决防止动态代理每次都加载反射生成新的类执行慢的问题
 */
public class GenerateHeadhunting {
    private static final Map<Class,Person> proxyFactory=new ConcurrentHashMap<Class,Person>();

    public static Person getInstance(Class<? extends Person> clazz){
        try{
            Person person = proxyFactory.get(clazz);
            if(person==null){
                synchronized (proxyFactory){
                    if(person==null){
                        person =(Person)Proxy.newProxyInstance(clazz.getClassLoader(),
                                clazz.getInterfaces(),
                                new Headhunting(clazz.newInstance()));
                        proxyFactory.put(clazz,person);
                        return person;
                    }
                }
            }else{
                return person;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
