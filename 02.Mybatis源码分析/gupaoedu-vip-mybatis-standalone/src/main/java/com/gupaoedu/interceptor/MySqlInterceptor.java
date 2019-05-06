package com.gupaoedu.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Statement;
import java.util.Properties;


/**
 * 统计sql 和执行的时间
 */
@Intercepts({@Signature(type = StatementHandler.class,method = "query",
        args = {Statement.class, ResultHandler.class})
})
public class MySqlInterceptor implements Interceptor {


    /**
     * 取值于 org.apache.ibatis.executor.statement.SimpleStatementHandler 当中的query方法
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        System.out.println("实际上执行的sql为"+boundSql.getSql());
        long start = System.currentTimeMillis();
        Object obj=invocation.proceed();
        long end = System.currentTimeMillis();
        System.out.println("实际上执行的时间是"+(end-start)+"ms");
        return obj;
    }

    @Override
    public Object plugin(Object o) {
        return  Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("这里是执行参数没什么好执行的");

    }
}
