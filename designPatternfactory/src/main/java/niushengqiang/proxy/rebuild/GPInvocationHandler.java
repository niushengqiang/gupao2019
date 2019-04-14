package niushengqiang.proxy.rebuild;

import java.lang.reflect.Method;

/**
 * 自定义一个动态代理的接口
 */
public interface GPInvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;
}
