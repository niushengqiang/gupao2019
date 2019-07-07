package com.niushengqiang.autostart.startter.first;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@Configurable
public class AConfiguration {

    @Bean
    @ConditionalOnClass

    private  A a(){
        return  new  A();
    }
}
