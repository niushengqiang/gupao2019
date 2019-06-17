package com.gupao.minitomcat.servletcore;


public abstract class GPHttpServlet {


    public  void  service (GPHttpServletRequest request, GPHttpServletResponse response){
        try{
            String method = request.getMethod();
            if("GET".equals(method)){
                this.doGet(request,response);
            }else{
                this.doPost(request,response);
            }
        }catch (Exception e){
            e.printStackTrace();
            try {
                response.write(" 500 error");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }

    public abstract void doGet(GPHttpServletRequest request,GPHttpServletResponse response) throws Exception;

    public abstract void doPost(GPHttpServletRequest request,GPHttpServletResponse response) throws Exception;
}

