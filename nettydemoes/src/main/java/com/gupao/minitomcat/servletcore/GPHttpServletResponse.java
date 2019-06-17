package com.gupao.minitomcat.servletcore;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

public class GPHttpServletResponse {

    private HttpRequest httpRequest;

    private ChannelHandlerContext chc;


    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public ChannelHandlerContext getChc() {
        return chc;
    }

    public void setChc(ChannelHandlerContext chc) {
        this.chc = chc;
    }

    public GPHttpServletResponse() {

    }

    public GPHttpServletResponse(HttpRequest httpRequest, ChannelHandlerContext chc) {
        this.httpRequest = httpRequest;
        this.chc = chc;
    }


    //设置的响应必须符合http协议标准， http协议其实就是一个字符串前缀
    public void write(String  out) throws Exception {
        try {
            if (out == null || out.length() == 0) {
                return;
            }
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(out.getBytes("UTF-8")));
            response.headers().set("Content-Type", "text/html;");
            chc.write(response);
        } finally {
            chc.flush();
            chc.close();
        }
    }
}