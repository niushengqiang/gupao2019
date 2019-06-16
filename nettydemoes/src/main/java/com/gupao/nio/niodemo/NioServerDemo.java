package com.gupao.nio.niodemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServerDemo {

    private int  port=8080;

    //选择器
    private Selector selector;

    //缓冲区,Buffer等候区
    private ByteBuffer buffer=ByteBuffer.allocate(2014);

    public NioServerDemo(int port) {
        try {
            this.port = port;
            ServerSocketChannel server = ServerSocketChannel.open();
            //我得告诉地址
            //IP/Port
            server.bind(new InetSocketAddress(this.port));
            //BIO 升级版本 NIO，为了兼容BIO，NIO模型默认是采用阻塞式
            server.configureBlocking(false);
            //大堂经理准备就绪，接客
            selector = Selector.open();
            //在门口翻牌子，正在营业
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //进行状态的轮询
    public  void listener(){
        try{
            while(true){
                int select = selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    this.processKey(next);
                }
            }
        }catch (Exception e){

        }
    }

    /**
     * 这里是真正的调用地方 用来处理io
     * 这里进行数据的读取,需要进行io状态的校验
     */
    private void processKey(SelectionKey key) throws IOException {
        if(key.isAcceptable()){
            //可接收的状态
            ServerSocketChannel server = (ServerSocketChannel)key.channel();
            SocketChannel channel = server.accept();
            channel.configureBlocking(false);
            //修改为可以读取的状态
            channel.register(selector,SelectionKey.OP_READ);
        }else if(key.isReadable()){
            //可读
            SocketChannel channel = (SocketChannel)key.channel();
            int len = channel.read(buffer);
            if(len>0){
                buffer.flip();
                String content = new String(buffer.array(), 0, len);
                channel.register(selector,SelectionKey.OP_WRITE);
                key.attach(content);
                System.out.println("接收到的内容为:"+content);
            }
        }else if(key.isWritable()){
            //可写
            SocketChannel channel = (SocketChannel)key.channel();
            String content = (String)key.attachment();
            channel.write(ByteBuffer.wrap(("输出：" + content).getBytes()));
            channel.close();
        }
    }

    public static void main(String[] args) {
        new NioServerDemo(8080).listener();
    }
}
