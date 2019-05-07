package com.gp.mibatis.myversion;

import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

public class Configuration {

    public static final ResourceBundle sqlMappings;

    static{
        sqlMappings = ResourceBundle.getBundle("sql");
    }

    public <T> T getMapper(Class clazz,GpSqlSession gpSqlSession){
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{clazz},
                new MapperProxy(gpSqlSession));
    }


}
