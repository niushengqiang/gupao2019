package com.gupao.minirpc.server;

import com.gupao.minirpc.HelloService;

@RpcService(value = HelloService.class,version = "1.0")
public class HelloServiceImplA  implements HelloService{

    @Override
    public String sayHello(String name) {
        System.out.println("HelloServiceImplA 被调用 args:"+name);
        return "A Hello "+name;
    }
}
