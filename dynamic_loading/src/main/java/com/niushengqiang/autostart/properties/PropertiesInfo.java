package com.niushengqiang.autostart.properties;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;


@ConfigurationProperties(prefix =PropertiesInfo.pre)
public class PropertiesInfo {
    public static final String pre="com.yurang.pro";

    private Map<String,String> info =new HashMap<>();

    public Map<String, String> getInfo() {
        return info;
    }

    public void setInfo(Map<String, String> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "PropertiesInfo{" +
                "info=" + info +
                '}';
    }
}
