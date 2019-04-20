package gpspring.framework.aop.aspect;

public interface GPAdvice {
    Object invokeAdviceMethod(GPJoinPoint joinPoint, Object returnValue, Throwable tx) throws Throwable;
}
