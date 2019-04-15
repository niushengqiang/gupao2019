package gpspring.framework.beans.support;

import gpspring.framework.beans.config.GPBeanDefinition;
import gpspring.framework.context.support.AbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends AbstractApplicationContext{

    //存储注册信息的BeanDefinition
    protected final Map<String, GPBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, GPBeanDefinition>();

    @Override
    protected void refresh() throws Exception{

    }

}
