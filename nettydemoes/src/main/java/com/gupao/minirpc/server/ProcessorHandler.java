package com.gupao.minirpc.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.util.StringUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


public class ProcessorHandler extends ChannelInboundHandlerAdapter {

    private Map<String,Object> handlerMap;


    public ProcessorHandler(  Map<String,Object> handlerMap) {
        this.handlerMap = handlerMap;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = new Object();
        RpcRequest request = (RpcRequest)msg;
        result = invoke(request);
        ctx.write(result);
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    private Object invoke(RpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //反射调用
        String serviceName=request.getClassName();
        String version=request.getVersion();
        //增加版本号的判断
        if(!StringUtils.isEmpty(version)){
            serviceName+="-"+version;
        }

        Object service=handlerMap.get(serviceName);
        if(service==null){
            throw new RuntimeException("service not found:"+serviceName);
        }

        Object[] args=request.getParameters(); //拿到客户端请求的参数
        Method method=null;
        if(args!=null) {
            Class<?>[] types = new Class[args.length]; //获得每个参数的类型
            for (int i = 0; i < args.length; i++) {
                types[i] = args[i].getClass();
            }
            Class clazz=Class.forName(request.getClassName()); //跟去请求的类进行加载
            method=clazz.getMethod(request.getMethodName(),types); //sayHello, saveUser找到这个类中的方法
        }else{
            Class clazz=Class.forName(request.getClassName()); //跟去请求的类进行加载
            method=clazz.getMethod(request.getMethodName()); //sayHello, saveUser找到这个类中的方法
        }

        Object result=method.invoke(service,args);//HelloServiceImpl 进行反射调用
        return result;
    }
}
