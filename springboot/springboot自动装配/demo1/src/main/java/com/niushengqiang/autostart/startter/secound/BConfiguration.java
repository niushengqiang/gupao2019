package com.niushengqiang.autostart.startter.secound;

import com.niushengqiang.autostart.startter.first.AConfiguration;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Configurable
@Import(AConfiguration.class)
public class BConfiguration {
    //这里加载的bean是基于 方法名字做bean的名称定义的
    @Bean
    public B ggb(){
        return  new B();
    }
}
