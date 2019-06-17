package com.gupao.minitomcat.businessimpl;

import com.gupao.minitomcat.servletcore.GPHttpServlet;
import com.gupao.minitomcat.servletcore.GPHttpServletRequest;
import com.gupao.minitomcat.servletcore.GPHttpServletResponse;

public class servletTwo extends GPHttpServlet {
    @Override
    public void doGet(GPHttpServletRequest request, GPHttpServletResponse response) throws Exception {
        this.doPost(request,response);
    }

    @Override
    public void doPost(GPHttpServletRequest request, GPHttpServletResponse response) throws Exception {
        response.write(" this is secound");
    }
}
