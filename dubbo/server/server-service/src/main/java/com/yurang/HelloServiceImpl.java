package com.yurang;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "hello"+ format.format(new Date());
    }
}
