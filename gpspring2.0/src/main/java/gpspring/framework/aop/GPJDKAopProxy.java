package gpspring.framework.aop;

import gpspring.framework.aop.intercept.GPMethodInvocation;
import gpspring.framework.aop.support.GPAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class GPJDKAopProxy implements GPAopProxy,InvocationHandler{
    private GPAdvisedSupport advised;

    public GPJDKAopProxy(GPAdvisedSupport config){
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return  Proxy.newProxyInstance(classLoader,
                        this.advised.getTargetClass().getInterfaces(),
                        this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicMethodMatchers = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method,
                this.advised.getTargetClass());
        GPMethodInvocation invocation = new GPMethodInvocation(proxy,this.advised.getTarget(),
                method,args,this.advised.getTargetClass(),interceptorsAndDynamicMethodMatchers);
        return invocation.proceed();
    }
}
