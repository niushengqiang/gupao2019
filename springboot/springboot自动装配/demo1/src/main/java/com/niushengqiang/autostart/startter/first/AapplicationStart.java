package com.niushengqiang.autostart.startter.first;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AapplicationStart {

    /**
     * spinrg3.0之后利用java的心特性来进行的基于注解是装载
     * @param args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AConfiguration.class);
        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();

        for (int i = 0; i < beanDefinitionNames.length; i++) {
            System.out.println(beanDefinitionNames[i]);
        }
    }
}
