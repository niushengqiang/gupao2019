package com.niushengqiang.autostart;

import com.alibaba.fastjson.JSONObject;

public class ObjJsonFormat implements ObjFormate{

    @Override
    public String format(Object o) {
        if(o!=null){
            return  JSONObject.toJSONString(o);
        }else{
            return "";
        }
    }
}
