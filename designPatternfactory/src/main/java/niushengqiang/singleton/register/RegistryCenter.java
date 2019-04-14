package niushengqiang.singleton.register;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这里是个单例的管理中心,默认所有的单例都可以由这里处理
 */
public class RegistryCenter {
    private static Map<String,Object> rc=new ConcurrentHashMap<String,Object>();
    public static Object getInstance(String className){
        try{
            if(!rc.containsKey(className)){
                synchronized (rc){
                    if(!rc.containsKey(className)){
                        Class<?> aClass = Class.forName(className);
                        Constructor<?> constructor = aClass.getConstructor(null);
                        constructor.setAccessible(true);
                        Object o = constructor.newInstance(null);
                        rc.put(className,o);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return rc.get(className);
    }
}
