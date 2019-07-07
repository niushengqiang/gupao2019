package com.niushengqiang.autostart.startter.third;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Configurable
@Import({DyamicLoadBeanSelector.class,DyamicLoadBeanRegistor.class})
public class CConfiguration {

    @Bean
    public C gereraic(){
        return  new C();
    }
}
