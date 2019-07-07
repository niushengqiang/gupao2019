package com.niushengqiang.autostart;


public class ObjStringFormat implements  ObjFormate{

    @Override
    public String format(Object o) {
        if(o!=null){
            return o.toString();
        }else{
            return "";
        }
    }
}
