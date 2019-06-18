package com.gupao.minirpc.client;

import com.gupao.minirpc.HelloService;

public class ClientStart {

    public static void main(String[] args) {

        RpcProxyClient rpcProxyClient = new RpcProxyClient();
        HelloService helloService = rpcProxyClient.clientProxy(HelloService.class,
                "localhost", 8080);
        String value = helloService.sayHello("张三");
        System.out.println("获取远程的执行结果"+value);
        
    }
}
