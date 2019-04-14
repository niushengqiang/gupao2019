package com.nsq.minispring.services;

import com.nsq.minispring.annotation.NsqService;

@NsqService
public class DemoServiceImpl implements DemoService{
    @Override
    public String ask(String str) {
        return "this is"+str;
    }
}
