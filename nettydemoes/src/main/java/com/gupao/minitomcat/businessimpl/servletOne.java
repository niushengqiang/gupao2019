package com.gupao.minitomcat.businessimpl;

import com.gupao.minitomcat.servletcore.GPHttpServlet;
import com.gupao.minitomcat.servletcore.GPHttpServletRequest;
import com.gupao.minitomcat.servletcore.GPHttpServletResponse;

public class servletOne extends GPHttpServlet {
    @Override
    public void doGet(GPHttpServletRequest request, GPHttpServletResponse response) {
        this.doPost(request,response);
    }

    @Override
    public void doPost(GPHttpServletRequest request, GPHttpServletResponse response) {
        try {
            response.write("This is First Serlvet");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
