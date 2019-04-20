package gpspring.framework.aop.aspect;

import gpspring.framework.aop.intercept.GPMethodInterceptor;
import gpspring.framework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

/**
 * 异常通知
 */
public class GPAfterThrowingAdviceInterceptor extends GPAbstractAspectAdvice implements GPAdvice,GPMethodInterceptor {

    private String throwingName;
    public GPAfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(GPMethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        }catch (Throwable e){
            Class<? extends Throwable> aClass = e.getClass();
            String fullName = aClass.getName();
            String simpleName = aClass.getSimpleName();
            if(fullName.equals(throwingName)
                    ||simpleName.equals(throwingName) ){
                invokeAdviceMethod(mi,null,e.getCause());
            }
            throw e;
        }
    }

    public void setThrowName(String throwName){
        this.throwingName = throwName;
    }
}
