package com.lagou.servlet;

import com.lagou.pojo.Request;
import com.lagou.pojo.Response;

public abstract class HttpServlet implements Servlet {

    public abstract void doGet(Request request, Response response) throws Exception;

    public abstract void doPost(Request request, Response response) throws Exception;

    @Override
    public void service(Request request, Response response) throws Exception {

        if("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request,response);
        }else {
            doPost(request,response);
        }
    }

}
