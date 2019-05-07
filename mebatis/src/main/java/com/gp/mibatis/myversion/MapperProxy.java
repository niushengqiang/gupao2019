package com.gp.mibatis.myversion;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperProxy implements InvocationHandler{

    private GpSqlSession gpSqlSession;

    public MapperProxy(GpSqlSession gpSqlSession) {
        this.gpSqlSession = gpSqlSession;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String mapperInterface = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String statementId = mapperInterface + "." + methodName;
        return gpSqlSession.selectOne(statementId, args[0]);
    }
}
