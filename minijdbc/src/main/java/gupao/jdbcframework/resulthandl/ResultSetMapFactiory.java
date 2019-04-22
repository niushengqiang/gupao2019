package gupao.jdbcframework.resulthandl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResultSetMapFactiory {

    private static Map<Class,ResultSetMap> cache=new ConcurrentHashMap<>();

    public static ResultSetMap getResultSetMap(Class clazz){
        ResultSetMap resultSetMap = cache.get(clazz);
        if(resultSetMap!=null){
            return resultSetMap;
        }else{
            DefaultResultSetMap<Class> classDefaultResultSetMap = new DefaultResultSetMap<>(clazz);
            cache.put(clazz,classDefaultResultSetMap);
            return classDefaultResultSetMap;
        }
    }

}
