package com.gupao.minitomcat;

import com.gupao.minitomcat.servletcore.GPHttpServlet;
import com.gupao.minitomcat.servletcore.GPHttpServletRequest;
import com.gupao.minitomcat.servletcore.GPHttpServletResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Tomcat {

    private int port;
    private Properties webxml = new Properties();

    public Tomcat(int port) {
        this.port = port;
    }


    //本地的url缓存
    private Map<String ,GPHttpServlet> mapper=new HashMap<>();

    //项目初始化
    public void init(){
        //加载本地web文件
        try{
            String WEB_INF = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(WEB_INF + "web.properties");
            webxml.load(fis);
            for (Object k : webxml.keySet()) {
                String key = k.toString();
                String valuesClazzName= (String) webxml.get(key);
                GPHttpServlet obj = (GPHttpServlet)Class.forName(valuesClazzName).newInstance();
                mapper.put("/"+key, obj);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    private void start() {
        this.init();
        //开启netty的监听
        NioEventLoopGroup bossgroup = new NioEventLoopGroup();
        NioEventLoopGroup workgroup = new NioEventLoopGroup();
        try{
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossgroup,workgroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel client) throws Exception {
                            //这里个人理解 为在线程池中自己进行处理的 链接连

                            //设置子处理类 编码类
                            client.pipeline().addLast(new HttpResponseEncoder());
                            //子处理类的   解码类
                            client.pipeline().addLast(new HttpRequestDecoder());

                            // 业务逻辑处理 这里需要自己书写的业务处理类
                            client.pipeline().addLast(new GPTomcatHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 启动服务器
            ChannelFuture f = server.bind(port).sync();
            System.out.println("GP Tomcat 已启动，监听的端口是：" + port);
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossgroup.shutdownGracefully();
            workgroup.shutdownGracefully();
        }
    }


    //自定义实现类
    private  class GPTomcatHandler  extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //进行http的处理
            if(msg instanceof HttpRequest){
                HttpRequest req = (HttpRequest) msg;
                //封装 request和response
                GPHttpServletRequest gpHttpServletRequest = new GPHttpServletRequest(req, ctx);
                GPHttpServletResponse GPHttpServletResponse = new GPHttpServletResponse(req,ctx);
                String url = gpHttpServletRequest.getUrl();
                if(mapper.containsKey(url)){
                    mapper.get(url).service(gpHttpServletRequest, GPHttpServletResponse);
                }else{
                    GPHttpServletResponse.write("404 - Not Found");
                }
            }
        }

        //异常的处理
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("netty 发生异常");
            cause.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Tomcat(8080).start();
    }


}
