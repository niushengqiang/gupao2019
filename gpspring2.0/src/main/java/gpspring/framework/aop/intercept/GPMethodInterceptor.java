package gpspring.framework.aop.intercept;

//方法的拦截器
public interface GPMethodInterceptor {
    Object invoke(GPMethodInvocation invocation) throws Throwable;
}
