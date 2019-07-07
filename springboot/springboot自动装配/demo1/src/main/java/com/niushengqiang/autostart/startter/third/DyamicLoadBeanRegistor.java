package com.niushengqiang.autostart.startter.third;

import com.niushengqiang.autostart.startter.secound.B;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 这里是实行注册式的bean加载
 */
public class DyamicLoadBeanRegistor implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
                                        BeanDefinitionRegistry beanDefinitionRegistry) {

        BeanDefinition beanDefinition = new RootBeanDefinition(B.class);
        beanDefinitionRegistry.registerBeanDefinition("xxBxx",beanDefinition);
    }
}
