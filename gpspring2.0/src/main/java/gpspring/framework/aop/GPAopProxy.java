package gpspring.framework.aop;

/**
 * 获取aop代理
 */
public interface GPAopProxy {

    Object getProxy();
    Object getProxy(ClassLoader classLoader);
}
