package com.gupao.minitomcat.servletcore;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

public class GPHttpServletRequest {

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

    public GPHttpServletRequest() {
    }

    public GPHttpServletRequest(HttpRequest httpRequest, ChannelHandlerContext chc) {
        this.httpRequest = httpRequest;
        this.chc = chc;
    }

    public String getUrl() {
        return httpRequest.uri();
    }


    public String getMethod() {
        return httpRequest.method().name();
    }

    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(httpRequest.uri());
        return decoder.parameters();
    }

    public String getParameter(String name) {
        Map<String, List<String>> params = getParameters();
        List<String> param = params.get(name);
        if (null == param) {
            return null;
        } else {
            return param.get(0);
        }
    }


}
