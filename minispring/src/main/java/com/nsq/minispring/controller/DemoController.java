package com.nsq.minispring.controller;

import com.nsq.minispring.annotation.NsqAutowired;
import com.nsq.minispring.annotation.NsqController;
import com.nsq.minispring.annotation.NsqlRequestParam;
import com.nsq.minispring.annotation.RequestMapping;
import com.nsq.minispring.services.DemoService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@NsqController
@RequestMapping("/c1")
public class DemoController {

    @NsqAutowired
    private DemoService demoService;

    @RequestMapping("/c2")
    public void ask(HttpServletRequest request, HttpServletResponse response,@NsqlRequestParam("name")String arg) throws IOException {
       try{
            PrintWriter writer = response.getWriter();
            writer.write(demoService.ask(arg));
       }catch (Exception e){
            e.printStackTrace();
       }
    }
}
