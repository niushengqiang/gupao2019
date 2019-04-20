package gpspring.framework.aop.aspect;

import java.lang.reflect.Method;

/**
 * 这是一个参数传递类
 */
public interface GPJoinPoint {
    Object getThis();

    Object[] getArguments();

    Method getMethod();

    void setUserAttribute(String key, Object value);

    Object getUserAttribute(String key);
}
