package gpspring.framework.aop;


import gpspring.framework.aop.support.GPAdvisedSupport;

public class GPCGlibAopProxy implements GPAopProxy{

    private GPAdvisedSupport advised;


    public GPCGlibAopProxy(GPAdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
