package gpspring.framework.aop.aspect;

import gpspring.framework.aop.intercept.GPMethodInterceptor;
import gpspring.framework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

public class GPMethodBeforeAdviceInterceptor extends  GPAbstractAspectAdvice implements GPAdvice,GPMethodInterceptor{

    private GPJoinPoint joinPoint;

    public GPMethodBeforeAdviceInterceptor(Method aspectMethod,Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }


    private void before(Method method,Object[] args,Object target) throws Throwable{
        //传送了给织入参数
        super.invokeAdviceMethod(this.joinPoint,null,null);
    }


    @Override
    public Object invoke(GPMethodInvocation invocation) throws Throwable {
        this.joinPoint=invocation;
        this.before(invocation.getMethod(),invocation.getArguments(),invocation.getThis());
        return invocation.proceed();
    }
}
