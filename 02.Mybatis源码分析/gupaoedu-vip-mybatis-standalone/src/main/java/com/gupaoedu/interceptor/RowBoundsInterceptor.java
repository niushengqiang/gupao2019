package com.gupaoedu.interceptor;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Properties;


/**
 *  niushengqiang
 */
@Intercepts({@Signature(type = Executor.class,method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class RowBoundsInterceptor implements Interceptor {

    Logger log=LoggerFactory.getLogger(RowBoundsInterceptor.class);

    public RowBoundsInterceptor() {
        System.out.println();
    }

    /**
     * @param invocation
     * @return
     * @throws Throwable
     * 因为RowBounds中的 sql语句是final的所以无法直接对其进行修改.更换为其它方式
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("将逻辑分页改为物理分页 ");
        Object[] args = invocation.getArgs();
        // 判断是否有rowBounds的参数
        MappedStatement ms= (MappedStatement) args[0];
        RowBounds rowBounds= (RowBounds) args[2];
        if(rowBounds==null){
            System.out.println("该方法无需进行分页处理");
            return  invocation.proceed();
        }else{
            SqlSource sqlSource1 = ms.getSqlSource();
            BoundSql boundSql1 = sqlSource1.getBoundSql(args[1]);
            String sql1 = boundSql1.getSql();
            StringBuffer sb = new StringBuffer(sql1);
            sb.append(" limit ");
            sb.append(rowBounds.getOffset());
            sb.append(",");
            sb.append(rowBounds.getLimit());
            String sql=sb.toString();
            // 自定义sqlSource
            SqlSource sqlSource = new StaticSqlSource(ms.getConfiguration(), sql, boundSql1.getParameterMappings());
            // 修改原来的sqlSource
            Field field = MappedStatement.class.getDeclaredField("sqlSource");
            field.setAccessible(true);
            field.set(ms, sqlSource);
            // 执行被拦截方法
            return invocation.proceed();
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("没有什么可以注入的参数");
    }
}
