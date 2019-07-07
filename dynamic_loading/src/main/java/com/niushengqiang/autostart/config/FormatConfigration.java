package com.niushengqiang.autostart.config;

import com.niushengqiang.autostart.ObjFormate;
import com.niushengqiang.autostart.ObjJsonFormat;
import com.niushengqiang.autostart.ObjStringFormat;
import com.niushengqiang.autostart.properties.PropertiesInfo;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;


@Configurable
@EnableConfigurationProperties(PropertiesInfo.class)
public class FormatConfigration {

    @Primary
    @ConditionalOnMissingClass("com.alibaba.fastjson.JSONObject")
    @Bean
    public  ObjFormate strFormat(){
        return new ObjStringFormat();
    }

    @Bean
    @ConditionalOnClass(name = "com.alibaba.fastjson.JSONObject")

    public  ObjFormate jsonFormat(){
        return new ObjJsonFormat();
    }
}
